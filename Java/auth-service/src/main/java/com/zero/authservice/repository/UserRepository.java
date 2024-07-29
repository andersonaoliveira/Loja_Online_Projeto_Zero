package com.zero.authservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import com.zero.authservice.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
