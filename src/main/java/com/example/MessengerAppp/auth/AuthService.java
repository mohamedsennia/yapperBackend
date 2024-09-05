package com.example.MessengerAppp.auth;

import com.example.MessengerAppp.conifg.JwtService;
import com.example.MessengerAppp.user.User;
import com.example.MessengerAppp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public CostumeResponse logIn(@RequestBody LogInRequest logInRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        logInRequest.getUserEmail(),logInRequest.getPassword()
                )
        );
        User user=this.userRepository.findByEmail(logInRequest.getUserEmail()).orElseThrow(()->new UsernameNotFoundException("user not found")) ;
        return CostumeResponse.builder().token(jwtService.generateToken(user)).role(user.getRole()).id(user.getId()).build();
    }
    public CostumeResponse signUp(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return CostumeResponse.builder().token(jwtService.generateToken(user)).role(user.getRole()).id(user.getId()).build();
    }
}
