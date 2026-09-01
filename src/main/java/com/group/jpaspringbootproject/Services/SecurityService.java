package com.group.jpaspringbootproject.Services;

import com.group.jpaspringbootproject.Models.Users;
import com.group.jpaspringbootproject.Repository.UsersRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

    public ResponseEntity<?> VerifyUserByUsernamePassword(String username, String password, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(username);
            ResponseCookie responseCookie = ResponseCookie.from("JWT_TOKEN",token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Lax")
                    .build();
            response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
                return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    public ResponseEntity<?> Logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("JWT_TOKEN", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Logged out successfully");
    }
}
