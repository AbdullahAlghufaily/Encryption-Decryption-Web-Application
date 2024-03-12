package com.youtube.jwt.service;

// AuthService.java

import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.User;
import com.youtube.jwt.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private JwtUtil tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userRepository;


    private PasswordEncoder passwordEncoder;

    public void processForgotPassword(String username) {
        // Validate email and generate/reset token
        Optional<User> optionalUser = userRepository.findByuserName(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String token = tokenService.generateToken(user);

            emailService.sendResetPasswordEmail(optionalUser.get().getUserEmail(),optionalUser.get().getuserPP());
        } else {
            System.out.println("User not found for email: " );
        }
    }
    }



