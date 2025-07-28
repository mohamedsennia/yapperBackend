package com.example.MessengerAppp.post;

import com.example.MessengerAppp.profile.Profile;
import com.example.MessengerAppp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Column(nullable = false,columnDefinition = "TEXT")
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
    @ManyToMany(mappedBy = "likedPosts")
    private Set<User> likes;
    public Post(String content,PostType postType){
        this.content=content;
        this.date=new Date();
        this.type=postType;
        this.replies=new ArrayList<>();
    }
    public Post(int id,String content){
        this.date=new Date();
        this.id=id;
        this.content=content;
    }
    public void toggleLiked(User user){
        if(likes.contains(user)){
            this.likes.remove(user);
        }else{
            this.likes.add(user);
        }
    }

}
