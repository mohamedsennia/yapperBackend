package com.example.MessengerAppp.auth;

import com.example.MessengerAppp.conifg.JwtService;
import com.example.MessengerAppp.exception.AlreadyExistsException;
import com.example.MessengerAppp.post.Post;
import com.example.MessengerAppp.profile.Profile;
import com.example.MessengerAppp.profile.ProfileRepositoty;
import com.example.MessengerAppp.profile.ProfileService;
import com.example.MessengerAppp.user.AddUserDTO;
import com.example.MessengerAppp.user.User;
import com.example.MessengerAppp.user.UserMapper;
import com.example.MessengerAppp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ProfileService  profileService;
    public CostumeResponse logIn(@RequestBody LogInRequest logInRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInRequest.getUserEmail(),logInRequest.getPassword()
                )
        );
        User user=this.userRepository.findByEmail(logInRequest.getUserEmail()).orElseThrow(()->new UsernameNotFoundException("user not found")) ;
        return CostumeResponse.builder().token(jwtService.generateToken(user)).role(user.getRole()).id(user.getId()).userName(user.getLastName()+" "+user.getFirstName()).build();
    }
    public CostumeResponse signUp(AddUserDTO user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(this.userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new AlreadyExistsException("Email already exists");
        }
        User user1= UserMapper.getUser(user);

        userRepository.save(user1);
        user1.setProfile(new Profile(user1));
        this.profileService.add(user1.getProfile());
        return CostumeResponse.builder().token(jwtService.generateToken(user1)).role(user1.getRole()).id(user1.getId()).build();
    }
}
