package com.scaler.bookmyshowfeb23.services;

import com.scaler.bookmyshowfeb23.models.User;
import com.scaler.bookmyshowfeb23.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User signUp(String email, String password) {
        Optional<User> optionalUser = userRepository.findAllByEmail(email);
        User user = null;

        if (optionalUser.isPresent()) {
            user = signIn(email, password);
        } else {
            user = new User();
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setEmail(email);
            user.setPassword(bCryptPasswordEncoder.encode(password));
            user = userRepository.save(user);
            //Create and register the user.
        }
        return user;
    }

    public User signIn(String email, String password) {
        //Implement the signIn workflow;
        Optional<User> optionalUser = userRepository.findAllByEmail(email);

        User user = optionalUser.get();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            System.out.println("Sign in successful");
        } else {
            System.out.println("Sign in unsuccessful");
        }
         return user;
    }
}
