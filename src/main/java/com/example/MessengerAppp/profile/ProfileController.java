package com.example.MessengerAppp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getCredentials());
    }
}
