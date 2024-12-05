package com.elkin.socialnetwork.services;

import com.elkin.socialnetwork.dto.ProfileDto;
import com.elkin.socialnetwork.dto.SignUpDto;
import com.elkin.socialnetwork.dto.UserDto;
import com.elkin.socialnetwork.dto.UserSummaryDto;

import java.util.List;

public interface UserService {

    ProfileDto getProfile(Long userId);
    void addFriend(Long friendId);
    List<UserSummaryDto> searchUsers(String term);

    UserDto signUp(SignUpDto user);
    void signOut(UserDto user);

}
