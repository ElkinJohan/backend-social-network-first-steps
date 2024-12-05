package com.elkin.socialnetwork.services.impl;

import com.elkin.socialnetwork.dto.CredentialsDto;
import com.elkin.socialnetwork.dto.UserDto;
import com.elkin.socialnetwork.services.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto authenticate(CredentialsDto credentialsDto) {
        String encodedMasterPassword = passwordEncoder.encode(CharBuffer.wrap("the-password"));
        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), encodedMasterPassword)) {
            return new UserDto(1L, "Elkin", "Imbachi", "login", "token");
        }
        throw new RuntimeException("Invalid password");
    }

    @Override
    public UserDto findByLogin(String login) {
        if ("login".equals(login)) {
            return new UserDto(1L, "Elkin", "Imbachi", "login", "token");
        }
        throw new RuntimeException("Invalid login");
    }
}
