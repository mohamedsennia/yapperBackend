package com.example.MessengerAppp.user;

import com.example.MessengerAppp.exception.NotFoundException;
import com.example.MessengerAppp.message.MessageDTO;
import com.example.MessengerAppp.message.MessageService;
import com.example.MessengerAppp.profile.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

//     public List<GetUserDTO> findByIdNot(int id){
//    return this.userRepository.findByIdNot(id).stream().map(user -> {
//            UserDTO userDTO = Mapper.toUserDTO(user);
//            List< MessageDTO> conversation=this.messageService.conversationBetween(id,user.getId());
//            if(!conversation.isEmpty()){
//                userDTO.setMessageDTO(conversation.getLast());
//            }
//        return userDTO;
//    }).collect(Collectors.toList());
//     }
//     public List<GetUserDTO> findUsersInConversationWith(int id){
//         return this.userRepository.findUsersInConversationWith(id).stream().map(user -> {
//             UserDTO userDTO = Mapper.toUserDTO(user);
//             List< MessageDTO> conversation=this.messageService.conversationBetween(id,user.getId());
//             if(!conversation.isEmpty()){
//                 userDTO.setMessageDTO(conversation.getLast());
//             }
//             return userDTO;
//         }).collect(Collectors.toList());
//     }
     public GetUserDTO findUserById(int id){
     return  this.userRepository.findById(id).map(UserMapper::toGetUserDTO).orElseThrow(
             ()->   new  NotFoundException("User not found")
     );

     }
         public List<GetUserDTO> findByFirstnameOrLastnameContaining(String name){
        return this.userRepository.findByFirstnameOrLastnameContaining(name).stream().map(
                user -> {
                    return  new GetUserDTO(user.getFirstName(), user.getLastName(), user.getProfile().getId());
                }
        ).collect(Collectors.toList());
     }

    public void follow(int id) {
        User currentUser=this.userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new RuntimeException());
        User secondUser=this.userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
            currentUser.follows(secondUser);
         this.userRepository.save(currentUser);
    }
    public void unfollows(int id){
        User currentUser=this.userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new RuntimeException());
        User secondUser=this.userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        currentUser.unfollows(secondUser);
        this.userRepository.save(currentUser);
    }
    public void removeFollower(int id){
        User currentUser=this.userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(()->new RuntimeException());
        User secondUser=this.userRepository.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        currentUser.removeFollower(secondUser);
        this.userRepository.save(currentUser);
    }
    public User getUserObjectByUserEmail(String email){
        return this.userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException(""));
    }
}
