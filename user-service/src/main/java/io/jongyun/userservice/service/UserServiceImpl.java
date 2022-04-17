package io.jongyun.userservice.service;

import io.jongyun.userservice.client.OrderServiceClient;
import io.jongyun.userservice.domain.User;
import io.jongyun.userservice.dto.ResponseOrder;
import io.jongyun.userservice.dto.UserDto;
import io.jongyun.userservice.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    //    private final RestTemplate restTemplate;
    private final Environment env;
    private final OrderServiceClient orderServiceClient;

    @Override
    public UserDto create(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User user = mapper.map(userDto, User.class);

        user.setEncryptedPwd(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDto userDto = new ModelMapper().map(user, UserDto.class);
//        List<ResponseOrder> orders = new ArrayList<>();

        // Using as rest template
//        String orderUrl = String.format(env.getProperty("order_service.url"), userId);
//        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl,
//            HttpMethod.GET, null,
//            new ParameterizedTypeReference<List<ResponseOrder>>() {
//            });
//        List<ResponseOrder> ordersList = orderListResponse.getBody();

        List<ResponseOrder> orders = orderServiceClient.getOrders(userId);
        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public Iterable<User> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(RuntimeException::new);
        UserDto userDto = new ModelMapper().map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("load user by username ={}", username);
        Optional<User> byEmail = userRepository.findByEmail(username);
        if (byEmail.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User user = byEmail.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
            user.getEncryptedPwd(), true, true, true, true, new ArrayList<>());
    }
}
