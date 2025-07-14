package com.example.MessengerAppp.post;

import com.example.MessengerAppp.profile.Profile;
import com.example.MessengerAppp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @SequenceGenerator(
            name = "Post_sequence",
            sequenceName = "Post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "Post_sequence"
    )
    private int id;
    @Lob
    @Column(nullable = false)
    private String content;
    private Date date;
    @Enumerated(EnumType.STRING)
    private PostType type;
    @ManyToOne
    @JoinColumn(name = "profileId")
    private Profile profile;
    @ManyToOne
    @JoinColumn(name = "parent")
    private Post parent;
    @OneToMany(mappedBy = "parent")
    private List<Post> replies;

}
