package com.example.MessengerAppp.message;

import com.example.MessengerAppp.profile.ProfileMapper;

public class MessageMapper {
    public static GetMessageDTO toMessageDTO(Message message){
        return  new GetMessageDTO(message.getId(), message.content, message.time, ProfileMapper.toGetProfileDTO(message.sender));
    }
}
