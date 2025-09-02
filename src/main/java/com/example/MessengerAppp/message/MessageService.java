package com.example.MessengerAppp.message;


import com.example.MessengerAppp.converstation.ConversationService;
import com.example.MessengerAppp.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
private MessagerRpository messagerRpository;
private ProfileService profileService;
private ConversationService conversationService;
@Autowired
    public MessageService(MessagerRpository messagerRpository,ProfileService profileService,ConversationService conversationService){
        this.messagerRpository=messagerRpository;
    }
//    public Message save(Message message){
//     return   this.messagerRpository.save(message);
//    }
    public void addMessage(AddMessageDTO addMessageDTO){
        //MessageMapper.
        Message message=MessageMapper.toMessage(addMessageDTO);
        message.setSender(profileService.getProfileSubjectById(addMessageDTO.getProfileId()));
        message.setConversation(conversationService.getConversationObjectById(addMessageDTO.getConversationId()));
        this.messagerRpository.save(message);
    }




}
