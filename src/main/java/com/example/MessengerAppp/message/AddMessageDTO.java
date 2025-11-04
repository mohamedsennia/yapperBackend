package com.example.MessengerAppp.message;

import com.example.MessengerAppp.converstation.Conversation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMessageDTO {
    private int profileId;
    private String content;
    private int conversationId;
    private int targetId;

}
