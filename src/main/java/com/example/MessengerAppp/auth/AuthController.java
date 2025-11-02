package com.example.MessengerAppp.auth;

import com.example.MessengerAppp.user.AddUserDTO;
import com.example.MessengerAppp.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(
        origins = {
                "http://localhost:4200",
                "https://senniayapper.netlify.app/"
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

private  final  AuthService authService;

        @PostMapping("/logIn")
        public ResponseEntity<CostumeResponse> logIn(@RequestBody LogInRequest logInRequest){

            return   this.authService.logIn(logInRequest);

        }
        @PostMapping("/signUp")
        public ResponseEntity<CostumeResponse> signUp(@Valid @RequestBody AddUserDTO user){

               return this.authService.signUp(user);
        }
        @PostMapping("/refresh")
        public ResponseEntity<Map<String,String>> refreshToken(@CookieValue("refresh_token")String refreshToken){
        return     this.authService.refreshToken(refreshToken);
        }



}
