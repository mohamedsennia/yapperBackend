package com.example.MessengerAppp.message;


import com.example.MessengerAppp.converstation.ConversationService;
import com.example.MessengerAppp.exception.AlreadyExistsException;
import com.example.MessengerAppp.exception.NotAuthorisedException;
import com.example.MessengerAppp.exception.NotFoundException;
import com.example.MessengerAppp.profile.Profile;
import com.example.MessengerAppp.profile.ProfileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService {
private MessagerRpository messagerRpository;
private ProfileService profileService;
private ConversationService conversationService;
private SimpMessagingTemplate messagingTemplate;
@Autowired
    public MessageService(MessagerRpository messagerRpository, ProfileService profileService, ConversationService conversationService, SimpMessagingTemplate messagingTemplate){
        this.messagerRpository=messagerRpository;
        this.profileService=profileService;
        this.conversationService=conversationService;
        this.messagingTemplate=messagingTemplate;
    }
//    public Message save(Message message){
//     return   this.messagerRpository.save(message);
//    }
    @Transactional
    public void sendMessage(AddMessageDTO addMessageDTO, Principal principal){

        Message message=MessageMapper.toMessage(addMessageDTO);
        if(profileService.getProfileObjectByUserEmail(principal.getName()).getId()!= addMessageDTO.getProfileId()){
            throw new NotAuthorisedException("User not authorised");
        }

        Profile sender=profileService.getProfileSubjectById(addMessageDTO.getProfileId());
        int conversationId=addMessageDTO.getConversationId();
        message.setSender(sender);

        if(conversationId==-1){
            Profile target=profileService.getProfileSubjectById(addMessageDTO.getTargetId());
            Set<Profile> participants=new HashSet<Profile>();
            participants.add(sender);
            participants.add(target);
            conversationId= this.conversationService.createConversation(participants);

            messagingTemplate.convertAndSendToUser(
                    String.valueOf(target.getId()),
                    "/notification/messages",
                    MessageMapper.toMessageDTO(message)
            );
        }else{
            messagingTemplate.convertAndSend(

                    "/conversation/"+String.valueOf(conversationId),
                    MessageMapper.toMessageDTO(message)
            );
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
