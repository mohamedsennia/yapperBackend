package com.example.MessengerAppp.converstation;

import com.example.MessengerAppp.exception.NotAuthorisedException;
import com.example.MessengerAppp.exception.NotFoundException;
import com.example.MessengerAppp.post.GetPostDTO;
import com.example.MessengerAppp.post.Post;
import com.example.MessengerAppp.post.PostMapper;
import com.example.MessengerAppp.profile.Profile;
import com.example.MessengerAppp.profile.ProfileService;
import com.example.MessengerAppp.user.User;
import com.example.MessengerAppp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConversationService {
    private ConversationRepository conversationRepository;
    private UserService userService;
    private final int conversationPageSize=10;
    @Autowired
    public  ConversationService(ConversationRepository conversationRepository,UserService userService){
        this.conversationRepository=conversationRepository;
        this.userService=userService;
    }
//    public Page<GetConversationDTO> getConversations(int pageNumber){
//
//        Pageable pageable =  PageRequest.of(pageNumber,this.conversationPageSize);
//        return toDtoPages(this.conversationRepository.findAllByProfileId(userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getProfile().getId(),pageable),pageable);
//    }
    public List<GetConversationDTO>getConversations(){
        int profileId=userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getProfile().getId();
        return this.conversationRepository.findAllByProfileId(profileId).stream().map(
                conversation -> {
                    GetConversationDTO getConversationDTO=ConversationMapper.toGetConversationDTO(conversation);
                    for(Profile p : conversation.getParticipants()){
                        if(p.getId()!=profileId){
                            getConversationDTO.setConversationName(p.getProfileName());
                            break;
                        }
                    }
                    return  getConversationDTO;
                }
        ).collect(Collectors.toList());
    }
    public Conversation getConversationObjectById(int id){
       return this.conversationRepository.findById(id).orElseThrow(()-> new NotFoundException("conversation Not found"));
    }
    public int getConversationBetween(int profileId1,int profileId2){
        Optional<Conversation> conversationBetweenProfiles = this.conversationRepository.findConversationBetweenProfiles(profileId1, profileId2);
        return conversationBetweenProfiles.map(Conversation::getId).orElse(-1);
    }
    public int createConversation(Set<Profile> participants){
        Conversation conversation=new Conversation();
        conversation.setType(ConversationType.Private);
        conversation.setMessages(new ArrayList<>());
        conversation.setParticipants(participants);
       return this.conversationRepository.save(conversation).getId();
    }
//    private Page<GetConversationDTO> toDtoPages(Page<Conversation> page, Pageable pageable){
//
//        return new PageImpl<GetConversationDTO>(
//                page.getContent().stream().map(ConversationMapper::toConversationDTO).collect(Collectors.toList())
//                ,pageable
//                ,page.getTotalElements()
//        );
//    }

}
