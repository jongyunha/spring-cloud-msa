package io.jongyun.userservice.repository;

import io.jongyun.userservice.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(String userId);

    Optional<User> findByEmail(String email);

}
