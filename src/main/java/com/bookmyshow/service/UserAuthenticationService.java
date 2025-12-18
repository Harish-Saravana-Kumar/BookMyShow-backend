package com.bookmyshow.service;

import com.bookmyshow.model.UserLogin;
import com.bookmyshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public UserLogin registerNewUser(String name, String email, String phone, String password) {
        if (userRepository.findByEmail(email).isPresent() || userRepository.findByPhone(phone).isPresent()) {
            throw new RuntimeException("User already exists with this email or phone");
        }

        UserLogin user = new UserLogin();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setLoginStatus(false);

        return userRepository.save(user);
    }

    public UserLogin authenticateUser(String email, String password) {
        Optional<UserLogin> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            UserLogin loggedInUser = user.get();
            loggedInUser.setLoginStatus(true);
            return userRepository.save(loggedInUser);
        }
        throw new RuntimeException("Invalid email or password");
    }

    public UserLogin authenticateByPhone(String phone, String password) {
        Optional<UserLogin> user = userRepository.findByPhone(phone);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            UserLogin loggedInUser = user.get();
            loggedInUser.setLoginStatus(true);
            return userRepository.save(loggedInUser);
        }
        throw new RuntimeException("Invalid phone or password");
    }

    public void logoutUser(String userId) {
        Optional<UserLogin> user = userRepository.findById(userId);
        if (user.isPresent()) {
            UserLogin loggedOutUser = user.get();
            loggedOutUser.setLoginStatus(false);
            userRepository.save(loggedOutUser);
        }
    }

}
