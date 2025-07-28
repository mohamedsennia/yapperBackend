package com.example.MessengerAppp.profile;

import com.example.MessengerAppp.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private ProfileRepositoty profileRepositoty;
    @Autowired
    public ProfileService(ProfileRepositoty profileRepositoty){
        this.profileRepositoty=profileRepositoty;
    }

        public void add(Profile  profile) {
        this.profileRepositoty.save(profile);
    }


    public GetProfileDTO getProfileById(int id) {
      return   this.profileRepositoty.findById(id).map(ProfileMapper::toGetProfileDTO).orElseThrow(()->new NotFoundException("Profile not Found"));
    }
    public Profile getProfileObjectByUserEmail(String email){
        return this.profileRepositoty.findByOwnerEmail(email).orElseThrow(()-> new RuntimeException(""));
    }
}
