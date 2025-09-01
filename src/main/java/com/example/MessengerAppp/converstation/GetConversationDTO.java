package com.example.MessengerAppp.converstation;

import com.example.MessengerAppp.message.GetMessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetConversationDTO {
    private int id;
    private GetMessageDTO lastMessage;
}
