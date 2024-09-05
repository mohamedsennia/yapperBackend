package com.example.MessengerAppp.user;

import com.example.MessengerAppp.Mapper;
import com.example.MessengerAppp.message.MessageDTO;
import com.example.MessengerAppp.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
private  UserRepository userRepository;
private MessageService messageService;
@Autowired
    public UserService(UserRepository userRepository, MessageService messageService){
    this.userRepository=userRepository;
    this.messageService=messageService;
     }
     public List<UserDTO> findByFirstnameOrLastnameContaining(String name, int excludedUserId){
        return this.userRepository.findByFirstnameOrLastnameContaining(name,excludedUserId).stream().map(
                user -> {
                    UserDTO userDTO = Mapper.toUserDTO(user);
                    List< MessageDTO> conversation=this.messageService.conversationBetween(excludedUserId,user.getId());
                    if(conversation.size()>0){
                        userDTO.setMessageDTO(conversation.getLast());
                    }
                    return userDTO;
                }
        ).collect(Collectors.toList());
     }
     public List<UserDTO> findByIdNot(int id){
    return this.userRepository.findByIdNot(id).stream().map(user -> {
            UserDTO userDTO = Mapper.toUserDTO(user);
            List< MessageDTO> conversation=this.messageService.conversationBetween(id,user.getId());
            if(!conversation.isEmpty()){
                userDTO.setMessageDTO(conversation.getLast());
            }
        return userDTO;
    }).collect(Collectors.toList());
     }
     public List<UserDTO> findUsersInConversationWith(int id){
         return this.userRepository.findUsersInConversationWith(id).stream().map(user -> {
             UserDTO userDTO = Mapper.toUserDTO(user);
             List< MessageDTO> conversation=this.messageService.conversationBetween(id,user.getId());
             if(!conversation.isEmpty()){
                 userDTO.setMessageDTO(conversation.getLast());
             }
             return userDTO;
         }).collect(Collectors.toList());
     }
     public UserDTO findUserById(int id){
     Optional<User> user=this.userRepository.findById(id);
     if(user.isPresent()){
         UserDTO userDTO=Mapper.toUserDTO(user.get());

         return userDTO;
     }else {
         return  null;
     }
     }

}
