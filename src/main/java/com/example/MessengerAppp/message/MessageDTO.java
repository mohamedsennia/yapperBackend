package com.example.MessengerAppp.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {
        int id;
    String content;
    Date time;
    int senderId;
    int recipientId;

}
