package com.example.MessengerAppp.profile;

import com.example.MessengerAppp.converstation.ConversationService;
import com.example.MessengerAppp.exception.NotAuthorisedException;
import com.example.MessengerAppp.exception.NotFoundException;
import com.example.MessengerAppp.user.User;
import com.example.MessengerAppp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private ProfileRepositoty profileRepositoty;
    private ConversationService conversationService;
    @Autowired
    public ProfileService(ProfileRepositoty profileRepositoty){
        this.profileRepositoty=profileRepositoty;
    }

        public void add(Profile  profile) {
        this.profileRepositoty.save(profile);
    }


    public GetProfileDTO getProfileById(int profileId,int id) {
      return   this.profileRepositoty.findById(id).map(profile -> {
          GetProfileDTO profileDTO=ProfileMapper.toGetProfileDTO(profile);
          Profile currentUserProfil=this.profileRepositoty.findById(profileId).orElseThrow(()->new NotFoundException("profile not valid"));
          if(!currentUserProfil.getOwner().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
              throw  new NotAuthorisedException("You are no authorised");
          }
          profileDTO.setFollowed(currentUserProfil.getFollowing().contains(this.profileRepositoty.findById(id).orElseThrow(()->new NotFoundException("Profile Not Found"))));
          profileDTO.setMe(currentUserProfil.getId()==id);
          profileDTO.setConversationId(this.conversationService.getConversationBetween(currentUserProfil.getId(),id));
          return profileDTO;
      }).orElseThrow(()->new NotFoundException("Profile not Found"));
    }
    public Profile getProfileObjectByUserEmail(String email){
        return this.profileRepositoty.findByOwnerEmail(email).orElseThrow(()-> new RuntimeException(""));
    }
    public void follow(Profile currentUserProfile, Profile secondUserProfile) {

        currentUserProfile.follows(secondUserProfile);
        this.profileRepositoty.save(currentUserProfile);
    }
    public void unfollows(Profile currentUserProfile, Profile secondUserProfile){

        currentUserProfile.unfollows(secondUserProfile);
        this.profileRepositoty.save(currentUserProfile);
    }
    public void removeFollower(int profileId,int id){

        Profile currentUserProfil=this.profileRepositoty.findById(profileId).orElseThrow(()->new NotFoundException("profile not valid"));
        if(!currentUserProfil.getOwner().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            throw  new NotAuthorisedException("You are no authorised");
        }
        Profile secondUserProfile=this.profileRepositoty.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        currentUserProfil.removeFollower(secondUserProfile);
    }


    public void toggleFollow(int profileId,int id) {
        Profile currentUserProfil=this.profileRepositoty.findById(profileId).orElseThrow(()->new NotFoundException("profile not valid"));
        if(!currentUserProfil.getOwner().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())){


            throw  new NotAuthorisedException("You are no authorised");
        }
        Profile secondUserProfile=this.profileRepositoty.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        if(currentUserProfil.getFollowing().contains(secondUserProfile)){
            this.unfollows(currentUserProfil,secondUserProfile);
            return;
        }
        this.follow(currentUserProfil,secondUserProfile);

    }
    public Profile getProfileSubjectById(int id){
        return this.profileRepositoty.findById(id).orElseThrow(()->new NotFoundException("Profile not found"));
    }

}
