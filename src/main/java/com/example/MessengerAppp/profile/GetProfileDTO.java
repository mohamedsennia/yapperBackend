package com.example.MessengerAppp.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileDTO {
    private int id;
       private String profileName;
    private int ownerId;
    private int subscribers;
    private int subscribtions;
    private boolean isFollowed;
    private boolean isMe;
    private int conversationId;

    public GetProfileDTO(int id, String profileName, int ownerId, int subscribers, int subscribtions) {
        this.id = id;
        this.profileName=profileName;
        this.ownerId = ownerId;
        this.subscribers = subscribers;
        this.subscribtions = subscribtions;
    }
}
