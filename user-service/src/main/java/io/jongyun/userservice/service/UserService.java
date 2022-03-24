package io.jongyun.userservice.service;

import io.jongyun.userservice.domain.User;
import io.jongyun.userservice.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto create(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<User> getUserByAll();

    UserDto getUserDetailsByEmail(String email);
}
