package com.example.MessengerAppp.user;

import com.example.MessengerAppp.message.MessageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    int id;
    String firstName;
    String lastName;
    MessageDTO messageDTO;
}
