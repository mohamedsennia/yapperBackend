package com.example.MessengerAppp.post;

import com.example.MessengerAppp.Mapper;
import com.example.MessengerAppp.exception.NotFoundException;
import com.example.MessengerAppp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserService userService;
    @Autowired
    public PostService(PostRepository postRepository,UserService userService){
        this.postRepository=postRepository;
        this.userService=userService;
    }
    public PostDTO getPostbyId(int id){
        return this.postRepository.findById(id)
                .map(Mapper::toPostDTO)
                .orElseThrow(() -> new NotFoundException("There's no post with this id"));
    }
    public List<PostDTO> getPostByProfileId(int profileId){

        return this.postRepository.findByProfileId(profileId).stream().map(Mapper::toPostDTO).collect(Collectors.toList());
    }
    public void post(PostDTO postDTO){
       // System.out.println( SecurityContextHolder.getContext().getAuthentication().getName());
        Post post=Mapper.toPost(postDTO);

        this.postRepository.save(post);
    }
}
