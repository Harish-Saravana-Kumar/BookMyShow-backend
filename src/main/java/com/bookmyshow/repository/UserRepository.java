package com.bookmyshow.repository;

import com.bookmyshow.model.UserLogin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserLogin, String> {
    Optional<UserLogin> findByEmail(String email);
    Optional<UserLogin> findByPhone(String phone);

}
