package io.jongyun.userservice.controller;

import io.jongyun.userservice.domain.User;
import io.jongyun.userservice.dto.RequestUserDto;
import io.jongyun.userservice.dto.ResponseUserDto;
import io.jongyun.userservice.dto.UserDto;
import io.jongyun.userservice.service.UserServiceImpl;
import io.jongyun.userservice.vo.Greeting;
import io.micrometer.core.annotation.Timed;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;
    private final UserServiceImpl userService;
    private final Environment env;

    @GetMapping("/health_check")
    @Timed(value = "users.stats", longTask = true)
    public String status() {
        return String.format("It's Working in User service on PORT"
                + "port(local.server.port)=%s"
                + ", token secret=%s"
                + ", token expiration time=%s",
            env.getProperty("local.server.port"),
            env.getProperty("token.secret"),
            env.getProperty("token.expiration_time")
        );
    }

    @GetMapping("/welcome")
    @Timed(value = "users.welcome", longTask = true)
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody RequestUserDto requestUserDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserDto userDto = mapper.map(requestUserDto, UserDto.class);
        userService.create(userDto);

        ResponseUserDto responseUserDto = mapper.map(userDto, ResponseUserDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUserDto);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUserDto>> getUsers() {
        Iterable<User> userList = userService.getUserByAll();

        List<ResponseUserDto> result = new ArrayList<>();
        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseUserDto.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUserDto> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUserDto result = new ModelMapper().map(userDto, ResponseUserDto.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
