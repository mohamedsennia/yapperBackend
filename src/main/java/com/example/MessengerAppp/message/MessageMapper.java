package com.example.MessengerAppp.message;

import com.example.MessengerAppp.profile.ProfileMapper;

import java.util.Date;

public class MessageMapper {
    public static GetMessageDTO toMessageDTO(Message message){
        return  new GetMessageDTO(message.getId(), message.content, message.time, ProfileMapper.toGetProfileDTO(message.sender));
    }
    public static Message toMessage(AddMessageDTO addMessageDTO){
        return new Message(addMessageDTO.getContent(),new Date());
    }
}
