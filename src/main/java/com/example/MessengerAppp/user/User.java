package com.example.MessengerAppp.user;

import com.example.MessengerAppp.message.Message;
import com.example.MessengerAppp.post.Post;
import com.example.MessengerAppp.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "_User")


@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {


    @Id
    @SequenceGenerator(
            name = "User_sequence",
            sequenceName = "User_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="User_sequence"
    )
    private int id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany( mappedBy = "sender")

    private List<Message> messagesSent;
    @OneToMany(mappedBy = "recipient")
    private List<Message> messagesReceived;
    @OneToOne(mappedBy = "owner")
    private Profile profile;
    @ManyToMany
    @JoinTable(
            name = "user_follows",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private Set<User> following;
    @ManyToMany(mappedBy = "following")
    private Set<User> followers;
    @ManyToMany
    @JoinTable(
            name="user_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> likedPosts;





    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }
    public User(int id, String firstName, String lastName, String email, String password, Role role,List<Message> messagesReceived,List<Message> messagesSent) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.messagesReceived=messagesReceived;
        this.messagesSent=messagesSent;
        this.following=new HashSet<>();
        this.followers=new HashSet<>();
    }
    public User(int id, String firstName, String lastName, String email, String password, Role role) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.messagesReceived=new ArrayList<>();
        this.messagesSent=new ArrayList<>();
        this.profile=new Profile(this);
        this.following=new HashSet<>();
        this.followers=new HashSet<>();
    }
    public User(String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = Role.User;
        this.messagesReceived=new ArrayList<>();
        this.messagesSent=new ArrayList<>();
        this.profile=null;
        this.following=new HashSet<>();
        this.followers=new HashSet<>();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Message> getMessagesSent() {
        return messagesSent;
    }

    public void setMessagesSent(List<Message> messagesSent) {
        this.messagesSent = messagesSent;
    }

    public List<Message> getMessagesReceived() {
        return messagesReceived;
    }

    public void setMessagesReceived(List<Message> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void follows(User user){
        this.following.add(user);
    }
    public void unfollows(User user){
        this.following.remove(user);
    }
    public void removeFollower(User user){
        this.followers.remove(user);
    }

}
