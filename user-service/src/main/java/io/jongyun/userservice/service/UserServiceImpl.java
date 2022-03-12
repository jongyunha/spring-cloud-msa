package io.jongyun.userservice.service;

import io.jongyun.userservice.domain.User;
import io.jongyun.userservice.dto.UserDto;
import io.jongyun.userservice.repository.UserRepository;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        User userEntity = mapper.map(userDto, User.class);
        userEntity.setEncryptedPwd("encrypted_password");
        userRepository.save(userEntity);
        return null;
    }
}
