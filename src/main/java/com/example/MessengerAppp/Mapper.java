package com.example.MessengerAppp;

import com.example.MessengerAppp.message.Message;
import com.example.MessengerAppp.message.MessageDTO;
import com.example.MessengerAppp.post.Post;
import com.example.MessengerAppp.post.PostDTO;
import com.example.MessengerAppp.profile.Profile;
import com.example.MessengerAppp.profile.ProfileDTO;
import com.example.MessengerAppp.user.Role;
import com.example.MessengerAppp.user.User;
import com.example.MessengerAppp.user.UserDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), null,user.getProfile().getId());
    }
    public static Profile toProfile(ProfileDTO profileDTO){
        return new Profile(profileDTO.getId(),profileDTO.getPosts().stream().map(Mapper::toPost).collect(Collectors.toList()),Mapper.toUser(profileDTO.getUser()));
    }
    public static ProfileDTO toProfileDTO(Profile profile){
        return new ProfileDTO(profile.getId(),Mapper.toUserDTO(profile.getOwner()),profile.getPosts().stream().map(Mapper::toPostDTO).collect(Collectors.toList()));
    }

    public static PostDTO toPostDTO(Post post) {
        return new PostDTO(post.getId(), post.getContent(), post.getDate(),post.getType(),Mapper.toUserDTO(post.getProfile().getOwner()),post.getReplies().stream().map(Mapper::toPostDTO).collect(Collectors.toList()));
    }

    public static Post toPost(PostDTO postDTO){
        return new Post(postDTO.getId(), postDTO.getContent(), postDTO.getDate(),postDTO.getType(),null,null,postDTO.getReplies().stream().map(Mapper::toPost).collect(Collectors.toList()));
    }
}
