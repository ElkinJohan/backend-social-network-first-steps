package com.elkin.socialnetwork.services;

import com.elkin.socialnetwork.dto.CredentialsDto;
import com.elkin.socialnetwork.dto.UserDto;

public interface AuthenticationService {

    UserDto authenticate(CredentialsDto credentialsDto);
    UserDto findByLogin(String login);
}
