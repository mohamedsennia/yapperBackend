package com.example.MessengerAppp;

import com.example.MessengerAppp.message.Message;
import com.example.MessengerAppp.message.MessageDTO;
import com.example.MessengerAppp.user.Role;
import com.example.MessengerAppp.user.User;
import com.example.MessengerAppp.user.UserDTO;

import java.util.ArrayList;

public class Mapper {
    public  static Message toMessage(MessageDTO messageDTO){
        return new Message(messageDTO.getId()
                ,
                messageDTO.getContent(),
                messageDTO.getTime(),
                new User(messageDTO.getSenderId(),"","","","", Role.User,new ArrayList<Message>(),new ArrayList<Message>()),
                new User(messageDTO.getRecipientId(),"","","","", Role.User,new ArrayList<Message>(),new ArrayList<Message>())
        );
    }
    public static MessageDTO toMessageDTO(Message message){
        return  new MessageDTO(message.getId(), message.getContent(),message.getTime(),message.getSender().getId(),message.getRecipient().getId());
    }
    public  static User toUser(UserDTO userDTO){
        return new User(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), "","",Role.User);
    }
    public static UserDTO toUserDTO(User user){
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), null);
    }
}
