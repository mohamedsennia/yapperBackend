package com.example.MessengerAppp.post;

import com.example.MessengerAppp.profile.ProfileMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PostMapper {
    public  static GetPostDTO toGetPostDTO(Post post){
        return new GetPostDTO(post.getId(), post.getContent(), post.getDate(),post.getType(), ProfileMapper.toGetProfileDTO(post.getProfile()),post.getReplies().size(),post.getLikes().size(),false);
    }
    public static Post toPost(AddPostDTO addPostDTO){
        return new Post(addPostDTO.getContent(),addPostDTO.getPostType());
    }

}
