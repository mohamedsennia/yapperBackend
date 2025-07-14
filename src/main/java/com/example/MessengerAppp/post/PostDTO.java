package com.example.MessengerAppp.post;

import com.example.MessengerAppp.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private int id;
    private String content;
    private Date date;
    private PostType type;
    private UserDTO user;
    private List<PostDTO> replies;
}
