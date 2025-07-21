package com.example.MessengerAppp.profile;

import com.example.MessengerAppp.Mapper;
import com.example.MessengerAppp.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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


    public ProfileDTO getProfileById(int id) {
        Optional<Profile> profile=this.profileRepositoty.findById(id);
        if(profile.isPresent() ){
            return Mapper.toProfileDTO(profile.get());
        }else {
            throw new NotFoundException("Profile doesn't exist");
        }
    }
}
