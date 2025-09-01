package com.example.MessengerAppp.profile;

import com.example.MessengerAppp.converstation.Conversation;
import com.example.MessengerAppp.message.Message;
import com.example.MessengerAppp.post.Post;
import com.example.MessengerAppp.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.jboss.logging.Messages;

import java.util.List;
import java.util.Set;

@Entity

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
//    @OneToMany( mappedBy = "sender")
//
//    private List<Message> messagesSent;
//    @OneToMany(mappedBy = "recipient")
//    private List<Message> messagesReceived;


    @ManyToMany
    @JoinTable(
            name = "profile_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<Profile> following;
    @ManyToMany(mappedBy = "following")
    private Set<Profile> followers;
    @ManyToMany(mappedBy = "likes")
    private Set<Post> likedPosts;
    @ManyToMany(mappedBy = "participants")
    private Set<Conversation> conversations;
    @OneToMany(mappedBy = "sender")
    private Set<Message> messagesSent;
    public Profile(User owner){
        this.owner=owner;
    }
    public void follows(Profile profile){
        this.following.add(profile);
    }
    public void unfollows(Profile profile){
        this.following.remove(profile);
    }
    public void removeFollower(Profile profile){
        this.followers.remove(profile);
    }
}
