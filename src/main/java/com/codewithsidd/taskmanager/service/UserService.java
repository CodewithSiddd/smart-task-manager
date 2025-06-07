package com.codewithsidd.taskmanager.service;

import com.codewithsidd.taskmanager.entity.User;
import com.codewithsidd.taskmanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    public User createOrUpdateFromTokenClaims(Map<String, Object> claims) {
        String id = (String) claims.get("sub");
        String email = (String) claims.get("email");
        String name = (String) claims.get("name");

        User user = userRepository.findById(id);
        if (user == null) {
            user = new User();
            user.setId(id);
            user.setEmail(email);
            user.setName(name);
            user.setRole("USER");
            user.setRegisteredAt(Instant.now());
        } else {
            user.setEmail(email); // update if changed
            user.setName(name);
        }

        userRepository.save(user);
        return user;
    }

}
