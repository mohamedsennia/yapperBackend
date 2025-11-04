package com.example.MessengerAppp.message;

import com.example.MessengerAppp.converstation.Conversation;
import com.example.MessengerAppp.profile.Profile;
import com.example.MessengerAppp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @SequenceGenerator(
            name = "Message_sequence",
            sequenceName = "Message_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="Message_sequence"
    )
    int id;

    String content;
    Date time;

    @ManyToOne
    @JoinColumn(name = "senderId")
    Profile sender;
    @ManyToOne
    @JoinColumn(name = "conversationId")
    Conversation conversation;

    public Message(String content, Date time) {
        this.content = content;
        this.time = time;

    }
}
