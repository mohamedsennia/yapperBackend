package com.example.MessengerAppp.user;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/search/{excludedUserId}/{name}")
    public List<UserDTO> searchUser(@PathVariable(name = "excludedUserId") int excludedUserId,@PathVariable(name = "name") String name){
        return this.userService.findByFirstnameOrLastnameContaining(name,excludedUserId);
    }
    @GetMapping("/getOtherUsers/{id}")
    List<UserDTO> findByIdNot(@PathVariable int id){
        return this.userService.findByIdNot(id);
    }
    @GetMapping("getConversations/{id}")
    List<UserDTO> findUsersInConversationWith(@PathVariable int id){
        return  this.userService.findUsersInConversationWith(id);
    }
    @GetMapping("getUser/{id}")
    UserDTO  findUserById(@PathVariable int id){
        System.out.println(id);
        return  this.userService.findUserById(id);
    }
}
