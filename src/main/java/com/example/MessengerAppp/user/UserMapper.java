package com.example.MessengerAppp.user;

public class UserMapper {
    public static GetUserDTO toGetUserDTO(User user){
            return new GetUserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getProfile().getId());
    }
    public static User getUser(AddUserDTO addUserDTO){
        return new User(addUserDTO.getFirstName(), addUserDTO.getLastName(), addUserDTO.getEmail(), addUserDTO.getPassword());
    }
}
