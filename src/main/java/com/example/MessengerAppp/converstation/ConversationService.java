package com.example.MessengerAppp.converstation;

import com.example.MessengerAppp.exception.NotAuthorisedException;
import com.example.MessengerAppp.post.GetPostDTO;
import com.example.MessengerAppp.post.Post;
import com.example.MessengerAppp.post.PostMapper;
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
    public Page<GetConversationDTO> getConversations(int profileId,int pageNumber){
        if (userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getProfile().getId()!=profileId){
            throw new NotAuthorisedException("Action not autorized");
        }
        Pageable pageable =  PageRequest.of(pageNumber,this.conversationPageSize);
        return toDtoPages(this.conversationRepository.findAllByProfileId(profileId,pageable),pageable);
    }
    private Page<GetConversationDTO> toDtoPages(Page<Conversation> page, Pageable pageable){

        return new PageImpl<GetConversationDTO>(
                page.getContent().stream().map(ConversationMapper::toConversationDTO).collect(Collectors.toList())
                ,pageable
                ,page.getTotalElements()
        );
    }

}
