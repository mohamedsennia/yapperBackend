package com.example.MessengerAppp.post;

import com.example.MessengerAppp.exception.NotAuthorisedException;
import com.example.MessengerAppp.exception.NotFoundException;
import com.example.MessengerAppp.profile.ProfileService;
import com.example.MessengerAppp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private ProfileService profileService;
    private UserService userService;
    private final int pageSize=10;
    @Autowired
    public PostService(PostRepository postRepository,ProfileService profileService,UserService userService){
        this.postRepository=postRepository;
        this.profileService=profileService;
        this.userService=userService;
    }
    public GetPostDTO getPostbyId(int id){
        return this.postRepository.findById(id)
                .map(PostMapper::toGetPostDTO)
                .orElseThrow(() -> new NotFoundException("There's no post with this id"));
    }
    public Page<GetPostDTO> getPostByProfileId(int profileId,int pageNumber){
        Pageable pageable =  PageRequest.of(pageNumber,this.pageSize);

        return this.toDtoPages(this.postRepository.findByProfileId(profileId,pageable),pageable);
    }
    public void post(AddPostDTO postDTO){


        Post post=PostMapper.toPost(postDTO);
        Optional<Post> parent=this.postRepository.findById(postDTO.getParent());
        parent.ifPresent(post::setParent);
        post.setProfile(profileService.getProfileObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()));

        this.postRepository.save(post);
    }
    public Page<GetPostDTO> getFeed(int pageNumber){
        Pageable pageable =  PageRequest.of(pageNumber,this.pageSize);
        Page<Post> page=this.postRepository.getFeed(SecurityContextHolder.getContext().getAuthentication().getName(),pageable);

       return this.toDtoPages(page,pageable);

    }
    public void deletePost(int postId){
       Post post=postRepository.findById(postId).orElseThrow(()->new NotFoundException("There's no post with this id"));
       if(!post.getProfile().getOwner().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
           throw new NotAuthorisedException("oops seems like you can't delete this post");
       }else{
           this.postRepository.delete(post);
       }
    }
    public void editPost(EditPostDTO editPostDTO){
        Post post=postRepository.findById(editPostDTO.getId()).orElseThrow(()->new NotFoundException("There's no post with this id"));
        if(!post.getProfile().getOwner().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw new NotAuthorisedException("oops seems like you can't edit this post");
        }else{
            post.setContent(editPostDTO.getContent());
            this.postRepository.save(post);
        }
    }
    public void toggleLike(int postId){
        Post post=postRepository.findById(postId).orElseThrow(()->new NotFoundException("There's no post with this id"));
        post.toggleLiked(this.userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        this.postRepository.save(post);
    }
    private Page<GetPostDTO> toDtoPages(Page<Post> page,Pageable pageable){

        return new PageImpl<GetPostDTO>(
                page.getContent().stream().map(PostMapper::toGetPostDTO).collect(Collectors.toList())
                ,pageable
                ,page.getTotalElements()
        );
    }

}
