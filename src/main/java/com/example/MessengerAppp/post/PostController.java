package com.example.MessengerAppp.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
public class PostController {
    private PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService=postService;
    }
    @GetMapping("/byProfile/{profileId}/{page}")
    public Page<GetPostDTO> getPostsByProfileId(@PathVariable(name = "profileId") int profileId,@PathVariable(name = "page") int page){



        return  postService.getPostByProfileId(profileId,page);
    }
    @PostMapping()
    public GetPostDTO post(@RequestBody AddPostDTO addPostDTO){

       return this.postService.post(addPostDTO);
    }
    @GetMapping("/feed/{page}")
    public Page<GetPostDTO> getFeed(@PathVariable int page){
        return this.postService.getFeed(page);
    }
    @GetMapping("/comments/{parentId}/{page}")
    public Page<GetPostDTO> getComments(@PathVariable(name = "parentId") int parentId,@PathVariable(name = "page") int page){

        return this.postService.getComments(parentId,page);
    }
    @PatchMapping()
    public void editPost(@RequestBody EditPostDTO editPostDTO){
        this.postService.editPost(editPostDTO);
    }
    @PatchMapping("/toggleLike/{postId}")
    public void toggleLike(@PathVariable int postId){
        this.postService.toggleLike(postId);
    }
}
