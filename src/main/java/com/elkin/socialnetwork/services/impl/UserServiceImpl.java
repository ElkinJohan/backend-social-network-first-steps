package com.elkin.socialnetwork.services.impl;

import com.elkin.socialnetwork.dto.*;
import com.elkin.socialnetwork.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public ProfileDto getProfile(Long userId) {
        return new ProfileDto(new UserSummaryDto(1L, "Elkin", "Imbachi"),
                Arrays.asList(new UserSummaryDto(2L, "Andrea", "Espinosa")),
                Arrays.asList(new MessageDto(1L, "Mi mensaje")),
                Arrays.asList(new ImageDto(1L, "Title", null)));
    }

    @Override
    public void addFriend(Long friendId) {
        return;
    }

    @Override
    public List<UserSummaryDto> searchUsers(String term) {
        return Arrays.asList(new UserSummaryDto(1L, "Elkin", "Imbachi"),
                new UserSummaryDto(2L, "Andrea", "Espinosa"));
    }

    @Override
    public UserDto signUp(SignUpDto user) {
        return new UserDto(1L, "Elkin", "Imbachi", "login", "token");
    }

    @Override
    public void signOut(UserDto user) {

    }
}
