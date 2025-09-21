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



    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> findUserById(@PathVariable int id){
        return new ResponseEntity<>(this.userService.findUserById(id), HttpStatus.OK);
    }


}
