package com.example.MessengerAppp.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
