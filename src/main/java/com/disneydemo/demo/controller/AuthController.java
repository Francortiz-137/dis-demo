package com.disneydemo.demo.controller;

import com.disneydemo.demo.DTO.DTO;
import com.disneydemo.demo.model.DisneyUser;
import com.disneydemo.demo.service.UserService;
import com.disneydemo.demo.service.implementation.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(
            @RequestParam String userName,@RequestParam String email, @RequestParam String password) {


        if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>(DTO.makeMap("error","Missing data"), HttpStatus.FORBIDDEN);
        }

        if (userService.findByEmail(email) !=  null) {
            return new ResponseEntity<>(DTO.makeMap("error","Email already in use"), HttpStatus.FORBIDDEN);
        }

        if (userService.findByUserName(userName) !=  null) {
            return new ResponseEntity<>(DTO.makeMap("error","User Name already in use"), HttpStatus.FORBIDDEN);
        }

        DisneyUser user = userService.saveUser(new DisneyUser(userName,email,passwordEncoder.encode(password)));


        try {
            String mail = send(email);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String send(String email) throws IOException {
        return mailService.sendTextEmail(email);
    }


    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsers(){

        List<DisneyUser> disneyUsers = userService.findAll();
        return new ResponseEntity<>(DTO.usersToDTO(disneyUsers),HttpStatus.OK);
    }

    public static boolean isGuest(Authentication authentication){
        //receive an authentication object if is guest or a logged user
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
}
