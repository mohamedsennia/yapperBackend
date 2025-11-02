package com.example.MessengerAppp.refreshToken;

import com.example.MessengerAppp.user.User;
import com.example.MessengerAppp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Ref;

@Service
public class RefreshTokenService {
    private UserService userService;
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    public RefreshTokenService(UserService userService,RefreshTokenRepository refreshTokenRepository){
            this.userService=userService;
            this.refreshTokenRepository=refreshTokenRepository;
    }
//    @Transactional
//    public void add(String token, UserDetails userDetails){
//        User user=userService.getUserObjectByUserEmail(userDetails.getUsername());
//        System.out.println();
//        this.refreshTokenRepository.save(new RefreshToken(token,user));
//    }
}
