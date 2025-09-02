package com.example.MessengerAppp.profile;

public class ProfileMapper {
    public static GetProfileDTO toGetProfileDTO(Profile profile){
        return new GetProfileDTO(profile.getId(), profile.getOwner().getFirstName(),profile.getOwner().getLastName(),profile.getOwner().getId(),profile.getFollowers().size(),profile.getFollowing().size());
    }
}
