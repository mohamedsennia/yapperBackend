package com.example.MessengerAppp.post;

import com.example.MessengerAppp.profile.GetProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostDTO {
private int id;
private String content;
private Date date;
private PostType type;
private GetProfileDTO profile;
private int commentsCount;
private int likesCount;
private boolean liked;
}
