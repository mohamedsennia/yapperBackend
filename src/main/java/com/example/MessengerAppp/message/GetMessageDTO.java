package com.example.MessengerAppp.message;

import com.example.MessengerAppp.profile.GetProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMessageDTO {
    private int id;
    private String content;
    private Date time;
    private GetProfileDTO sender;
    private boolean isMine;

    public GetMessageDTO(int id, String content, Date time, GetProfileDTO sender) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.sender = sender;
    }
}
