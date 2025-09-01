package com.example.MessengerAppp.converstation;

import com.example.MessengerAppp.message.Message;
import com.example.MessengerAppp.profile.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    @Id
    @SequenceGenerator(name = "conversation_sequence",sequenceName = "conversation_squence",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "conversation_sequence")
    private int id;
    @OneToMany
    @JoinColumn(name = "conversationId")
    Set<Message> messages;
    @ManyToMany
    @JoinTable(
            name="in_conversation",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    Set<Profile> participants;
}
