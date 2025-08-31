package com.example.MessengerAppp.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserDTO {

    private int id;
    private String firstName;
    private String lastName;
    private int profile;
    private String email;
    private int subscribers;
    private int subscribtions;
    private boolean isFollowed;
    private boolean isMe;

}
