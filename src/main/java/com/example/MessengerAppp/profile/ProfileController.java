package com.example.MessengerAppp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/profile")
public class ProfileController {
    private  ProfileService profileService;
    @Autowired
    public ProfileController(ProfileService profileService){
        this.profileService=profileService;
    }
    @GetMapping()
    public void getProfiles(){
        //System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GetProfileDTO> getProfileById(@PathVariable(name = "id") int id){
        return new ResponseEntity<>(this.profileService.getProfileById(id), HttpStatus.OK);
    }
    @GetMapping("/search/{keyWord}")
    public ResponseEntity<List<GetProfileDTO>> searchProfile(@PathVariable(name = "keyWord") String keyWord){
        return new ResponseEntity<>(this.profileService.searchProfile(keyWord),HttpStatus.OK );
    }
    @PutMapping("/toggleFollow/{id}")
    public void toggleFollow(@PathVariable(name = "id") int id){
        this.profileService.toggleFollow(id);
    }
    @PutMapping("/removeFollower/{id}")
    public void removeFollower(@PathVariable int id){
        this.profileService.removeFollower(id);
    }

}
