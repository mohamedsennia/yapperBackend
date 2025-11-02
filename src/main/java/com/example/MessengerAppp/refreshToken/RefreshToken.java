package com.example.MessengerAppp.refreshToken;

import com.example.MessengerAppp.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {
    @Id
   private String token;
    @OneToOne(mappedBy = "refreshToken")
   private User owner;

}
