package com.elkin.socialnetwork.services;

import com.elkin.socialnetwork.dto.ImageDto;
import com.elkin.socialnetwork.dto.MessageDto;
import com.elkin.socialnetwork.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommunityService {

    List<MessageDto> getCommunityMessages(UserDto user, int page);
    List<ImageDto> getCommunityImages(int page);
    MessageDto postMessage(MessageDto messageDto);
    ImageDto postImage(MultipartFile file, String title);
}
