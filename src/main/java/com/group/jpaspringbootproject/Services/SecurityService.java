package com.group.jpaspringbootproject.Services;

import com.group.jpaspringbootproject.Models.Users;
import com.group.jpaspringbootproject.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    @Autowired
    JwtService jwtService;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<?> RegisterUser(Users users) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(usersRepo.save(users));
    }

    public ResponseEntity<?> VerifyUserByUsernamePassword(String username, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().body(jwtService.generateToken(username));
            //            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
