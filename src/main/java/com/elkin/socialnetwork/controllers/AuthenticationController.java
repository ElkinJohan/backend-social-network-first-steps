package com.elkin.socialnetwork.controllers;

import com.elkin.socialnetwork.config.UserAuthenticationProvider;
import com.elkin.socialnetwork.dto.SignUpDto;
import com.elkin.socialnetwork.dto.UserDto;
import com.elkin.socialnetwork.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

    private final UserService userService;
    private final UserAuthenticationProvider userAuthenticationProvider;


    public AuthenticationController(UserService userService, UserAuthenticationProvider userAuthenticationProvider) {
        this.userService = userService;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @PostMapping("/signIn")
    public ResponseEntity<UserDto> signIn(@AuthenticationPrincipal UserDto user){
        user.setToken(userAuthenticationProvider.createToken(user.getLogin()));
        return ResponseEntity.ok(user);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid SignUpDto user){
        UserDto createdUser = userService.signUp(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId() + "/profile")).body(createdUser);
    }

    @PostMapping("/signOut")
    public ResponseEntity<Void> signOut(@AuthenticationPrincipal UserDto user){
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }



}
