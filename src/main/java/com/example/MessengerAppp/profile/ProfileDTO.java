package com.example.MessengerAppp.profile;

import com.example.MessengerAppp.post.PostDTO;
import com.example.MessengerAppp.post.PostType;
import com.example.MessengerAppp.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDTO {
    private int id;
    private UserDTO user;
    List<PostDTO> posts;
}
