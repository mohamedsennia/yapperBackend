package com.example.MessengerAppp.user;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@NoArgsConstructor
@RequestMapping("/api/Utilisateur")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

//    @GetMapping("/getOtherUsers/{id}")
//    List<UserDTO> findByIdNot(@PathVariable int id){
//        return this.userService.findByIdNot(id);
//    }
//    @GetMapping("getConversations/{id}")
//    List<UserDTO> findUsersInConversationWith(@PathVariable int id){
//        return  this.userService.findUsersInConversationWith(id);
//    }
    @GetMapping("/search/{name}")
    public ResponseEntity<List<GetUserDTO> > searchUser(@PathVariable(name = "name") String name){
        return new ResponseEntity<>(this.userService.findByFirstnameOrLastnameContaining(name),HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> findUserById(@PathVariable int id){
        return new ResponseEntity<>(this.userService.findUserById(id), HttpStatus.OK);
    }
    @PutMapping("/follow/{id}")
    public void follow(@PathVariable int id){
        this.userService.follow(id);
    }
    @PutMapping("/unfollow/{id}")
    public void unfollow(@PathVariable int id){
        this.userService.unfollows(id);
    }
    @PutMapping("/removeFollower/{id}")
    public void removeFollower(@PathVariable int id){
        this.userService.removeFollower(id);
    }
}
