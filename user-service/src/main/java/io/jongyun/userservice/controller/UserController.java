package io.jongyun.userservice.controller;

import io.jongyun.userservice.domain.User;
import io.jongyun.userservice.dto.RequestUserDto;
import io.jongyun.userservice.dto.ResponseUserDto;
import io.jongyun.userservice.dto.UserDto;
import io.jongyun.userservice.service.UserService;
import io.jongyun.userservice.service.UserServiceImpl;
import io.jongyun.userservice.vo.Greeting;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final Greeting greeting;
    private final UserServiceImpl userService;

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User service on PORT";
    }

    @GetMapping("/welcome")
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
