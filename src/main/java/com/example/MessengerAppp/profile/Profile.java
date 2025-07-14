package com.example.MessengerAppp.profile;

import com.example.MessengerAppp.post.Post;
import com.example.MessengerAppp.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Profile {
    @Id
    @SequenceGenerator(name = "Profile_sequence",
    allocationSize =1,
            sequenceName = "Profile_sequence"
    )
    @GeneratedValue(
            generator = "Profile_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private int id;
    @OneToMany(mappedBy = "profile")
    private List<Post> posts;
    @OneToOne
    @JoinColumn(name = "owner")
    private User owner;
    public Profile(User owner){
        this.owner=owner;
    }
}
