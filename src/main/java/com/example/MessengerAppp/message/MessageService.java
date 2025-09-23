package com.example.MessengerAppp.message;


import com.example.MessengerAppp.converstation.ConversationService;
import com.example.MessengerAppp.exception.AlreadyExistsException;
import com.example.MessengerAppp.exception.NotAuthorisedException;
import com.example.MessengerAppp.exception.NotFoundException;
import com.example.MessengerAppp.profile.Profile;
import com.example.MessengerAppp.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService {
private MessagerRpository messagerRpository;
private ProfileService profileService;
private ConversationService conversationService;

@Autowired
    public MessageService(MessagerRpository messagerRpository,ProfileService profileService,ConversationService conversationService){
        this.messagerRpository=messagerRpository;
        this.profileService=profileService;
        this.conversationService=conversationService;
    }
//    public Message save(Message message){
//     return   this.messagerRpository.save(message);
//    }
    public void newConversation(AddMessageDTO addMessageDTO,int targetId){
        //MessageMapper.
        Message message=MessageMapper.toMessage(addMessageDTO);
        if(profileService.getProfileObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId()!= addMessageDTO.getProfileId()){
            throw new NotAuthorisedException("User not authorised");
        }
        Profile sender=profileService.getProfileSubjectById(addMessageDTO.getProfileId());

        message.setSender(sender);
        int conversationId=addMessageDTO.getConversationId();
        if(conversationId==-1){
            Profile target=profileService.getProfileSubjectById(targetId);
            Set<Profile> participants=new HashSet<Profile>();
            participants.add(sender);
            participants.add(target);
            conversationId= this.conversationService.createConversation(participants);
        }else{
            return;
        }
        message.setConversation(conversationService.getConversationObjectById(conversationId));
        this.messagerRpository.save(message);
    }
    public void sendMessage(AddMessageDTO addMessageDTO){

        Message message=MessageMapper.toMessage(addMessageDTO);
        if(profileService.getProfileObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId()!= addMessageDTO.getProfileId()){
            throw new NotAuthorisedException("User not authorised");
        }

        Profile sender=profileService.getProfileSubjectById(addMessageDTO.getProfileId());

        message.setSender(sender);
        int conversationId=addMessageDTO.getConversationId();
        if(conversationId==-1){
            throw  new NotFoundException("Conversation doesn't exist");
        }
        message.setConversation(conversationService.getConversationObjectById(conversationId));
        this.messagerRpository.save(message);
    }

    public List<GetMessageDTO> getMessagesByConversationId(int id){
            return this.messagerRpository.findByConversationId(id).stream().map(message -> {
                GetMessageDTO messageDTO=MessageMapper.toMessageDTO(message);

                messageDTO.setMine(profileService.getProfileObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getId()==message.sender.getId());
                return messageDTO;
            }).collect(Collectors.toList());

    }




}
