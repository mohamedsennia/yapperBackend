package com.example.MessengerAppp.profile;

import com.example.MessengerAppp.converstation.ConversationService;
import com.example.MessengerAppp.exception.NotAuthorisedException;
import com.example.MessengerAppp.exception.NotFoundException;
import com.example.MessengerAppp.user.User;
import com.example.MessengerAppp.user.UserRepository;
import com.example.MessengerAppp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private ProfileRepositoty profileRepositoty;
    private ConversationService conversationService;
    private UserService userService;
    @Autowired
    public ProfileService(ProfileRepositoty profileRepositoty,ConversationService conversationService,UserService userService){
        this.profileRepositoty=profileRepositoty;
        this.conversationService=conversationService;
        this.userService=userService;
    }

        public void add(Profile  profile) {
        this.profileRepositoty.save(profile);
    }


    public GetProfileDTO getProfileById(int id) {

      return   this.profileRepositoty.findById(id).map(profile -> {
          GetProfileDTO profileDTO=ProfileMapper.toGetProfileDTO(profile);
          Profile currentUserProfil=this.userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getProfile();
          profileDTO.setFollowed(currentUserProfil.getFollowing().contains(this.profileRepositoty.findById(id).orElseThrow(()->new NotFoundException("Profile Not Found"))));
          profileDTO.setMe(currentUserProfil.getId()==id);
          profileDTO.setConversationId(this.conversationService.getConversationBetween(currentUserProfil.getId(),id));


          return profileDTO;
      }).orElseThrow(()->new NotFoundException("Profile not Found"));
    }
    public Profile getProfileObjectByUserEmail(String email){
        return this.profileRepositoty.findByOwnerEmail(email).orElseThrow(()-> new RuntimeException(""));
    }
    public void follow( Profile secondUserProfile) {
        Profile currentUserProfile=this.userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getProfile();
        currentUserProfile.follows(secondUserProfile);
        this.profileRepositoty.save(currentUserProfile);
    }
    public void unfollows( Profile secondUserProfile){
        Profile currentUserProfile=this.userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getProfile();
        currentUserProfile.unfollows(secondUserProfile);
        this.profileRepositoty.save(currentUserProfile);
    }
    public void removeFollower(int id){

        Profile currentUserProfile=this.userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getProfile();

        Profile secondUserProfile=this.profileRepositoty.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        currentUserProfile.removeFollower(secondUserProfile);
    }


    public void toggleFollow(int id) {
        Profile currentUserProfile=this.userService.getUserObjectByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getProfile();

        Profile secondUserProfile=this.profileRepositoty.findById(id).orElseThrow(()->new NotFoundException("User not found"));
        if(currentUserProfile.getFollowing().contains(secondUserProfile)){
            this.unfollows(secondUserProfile);
            return;
        }
        this.follow(secondUserProfile);

    }

    public Profile getProfileSubjectById(int id){
        return this.profileRepositoty.findById(id).orElseThrow(()->new NotFoundException("Profile not found"));
    }
    public List<GetProfileDTO> searchProfile(String keyword){
      return   this.profileRepositoty.searchProfile(keyword).stream().map(ProfileMapper::toGetProfileDTO).collect(Collectors.toList());
    }
}
