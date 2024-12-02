package org.arle.apirestdocker.application.port.out;

import org.arle.apirestdocker.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
    boolean existsByUsername(String username);
}
