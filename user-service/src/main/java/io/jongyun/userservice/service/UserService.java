package io.jongyun.userservice.service;

import io.jongyun.userservice.domain.User;
import io.jongyun.userservice.dto.UserDto;

public interface UserService {

    UserDto create(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<User> getUserByAll();

}
