package com.example.MessengerAppp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{myProfile}/{id}")
    public ResponseEntity<GetProfileDTO> getProfileById(@PathVariable(name = "myProfile") int myProfile,@PathVariable(name = "id") int id){
        return new ResponseEntity<>(this.profileService.getProfileById(myProfile,id), HttpStatus.OK);
    }
    @PutMapping("/toggleFollow/{myId}/{id}")
    public void toggleFollow(@PathVariable(name="myId") int myId,@PathVariable(name = "id") int id){
        this.profileService.toggleFollow(myId,id);
    }
    @PutMapping("/removeFollower/{myId}/{id}")
    public void removeFollower(@PathVariable(name="myId") int myId,@PathVariable int id){
        this.profileService.removeFollower(myId,id);
    }
}
