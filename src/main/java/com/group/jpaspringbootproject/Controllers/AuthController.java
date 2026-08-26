package com.group.jpaspringbootproject.Controllers;

import com.group.jpaspringbootproject.Models.Users;
import com.group.jpaspringbootproject.Services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    @Autowired
    SecurityService securityService;

//    @Autowired
//    ModelAndView mv;

    @PostMapping("/register")
    ResponseEntity<?> Register(@RequestBody Users users) {
        return securityService.RegisterUser(users);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("username") String username,
            @RequestParam("password")  String password){

        return securityService.VerifyUserByUsernamePassword(username,password);
    }
}
