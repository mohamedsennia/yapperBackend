package com.example.MessengerAppp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ProfileDTO> getProfileById(@PathVariable int id){
        return new ResponseEntity<>(this.profileService.getProfileById(id), HttpStatus.OK);
    }
}
