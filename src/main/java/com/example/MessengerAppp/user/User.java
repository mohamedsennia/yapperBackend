package com.example.MessengerAppp.user;

import com.example.MessengerAppp.message.Message;
import com.example.MessengerAppp.post.Post;
import com.example.MessengerAppp.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "_User")

@Data
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
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "sender")
    private List<Message> messagesSent;
    @OneToMany(mappedBy = "recipient")
    private List<Message> messagesReceived;
    @OneToOne(mappedBy = "owner")
    private Profile profile;







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
    }
    public User(String firstName, String lastName, String email, String password, Role role) {
        System.out.println("hhh");
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.messagesReceived=new ArrayList<>();
        this.messagesSent=new ArrayList<>();
        this.profile=new Profile(this);
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
}
