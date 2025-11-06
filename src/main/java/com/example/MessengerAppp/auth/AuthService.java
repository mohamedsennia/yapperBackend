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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ProfileService  profileService;
    public ResponseEntity<CostumeResponse> logIn(@RequestBody LogInRequest logInRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInRequest.getUserEmail(),logInRequest.getPassword()
                )
        );
        User user=this.userRepository.findByEmail(logInRequest.getUserEmail()).orElseThrow(()->new UsernameNotFoundException("user not found")) ;
        ResponseCookie cookie= ResponseCookie.from("refresh_token",jwtService.generateRefreshToken(user)).httpOnly(true).path("/api/auth/refresh").maxAge(  60 * 60 * 24 * 7).sameSite("Strict").secure(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(CostumeResponse.builder().token(jwtService.generateToken(user)).role(user.getRole()).id(user.getId()).userName(user.getLastName()+" "+user.getFirstName()).profileId(user.getProfile().getId()).build());
    }
    public ResponseEntity<CostumeResponse>  signUp(AddUserDTO user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(this.userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new AlreadyExistsException("Email already exists");
        }
        User user1= UserMapper.getUser(user);

        userRepository.save(user1);
        user1.setProfile(new Profile(user1));
        this.profileService.add(user1.getProfile());
        ResponseCookie cookie= ResponseCookie.from("refresh_token",jwtService.generateRefreshToken(user1)).httpOnly(true).path("/api/auth/refresh").maxAge(  60 * 60 * 24 * 7).sameSite("Strict").secure(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(CostumeResponse.builder().token(jwtService.generateToken(user1)).role(user1.getRole()).id(user1.getId()).userName(user1.getLastName()+" "+user1.getFirstName()).profileId(user1.getProfile().getId()).build());
    }

    public ResponseEntity<Map<String,String>> refreshToken(String refreshToken) {

        return new ResponseEntity<>(Map.of("token",this.jwtService.generateToken(refreshToken)),HttpStatus.OK);
    }
}
