package com.example.MessengerAppp.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProfileDTO {
    private int id;
    private String ownerFirstName;
    private String ownerLastName;
    private int ownerId;
}
