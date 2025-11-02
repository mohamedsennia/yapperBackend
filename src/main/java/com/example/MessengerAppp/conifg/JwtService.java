package com.example.MessengerAppp.conifg;

import com.example.MessengerAppp.exception.NotAuthorisedException;
import com.example.MessengerAppp.refreshToken.RefreshToken;
import com.example.MessengerAppp.refreshToken.RefreshTokenService;
import com.example.MessengerAppp.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static  final  String Secret_key="a48cec2c2bf5c1c4b53ac3525b732e08e23d05e7d9a66aef584c2f1ff97e4b8f";
    private RefreshTokenService refreshTokenService;
    private UserDetailsService userDetailsService;
    @Autowired
    public JwtService( RefreshTokenService refreshTokenService,UserDetailsService userDetailsService){
        this.refreshTokenService=refreshTokenService;
        this.userDetailsService=userDetailsService;
    }
    public String extractUsername(String jwt) {
        return extractClaim(jwt,Claims::getSubject);
    }
    private Claims extractAllClaims(String  jwt){
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(jwt).getBody();
    }
    private <T> T extractClaim(String token,Function<Claims,T> claimResolver){

        Claims claims=extractAllClaims(token);
        return claimResolver.apply(claims);
    }
    private Key getSigningKey() {
        byte[] ByteKey= Decoders.BASE64.decode(Secret_key);
        Key key =Keys.hmacShaKeyFor(ByteKey);

        return key;
    }
    public String generateToken( UserDetails userDetails){
        return this.generateToken(new HashMap<>(),userDetails);
    }
    public String generateToken(Map<String,Object> extraClaims, UserDetails userDetails){
       return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60))
               .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public String generateToken(String refreshToken){
        String userEmail=extractUsername(refreshToken);
        UserDetails userDetails=userDetailsService.loadUserByUsername(userEmail);
        if(!isValidToken(refreshToken,userDetails)){
            throw  new NotAuthorisedException("You are not authorised");
        }
      return   this.generateToken(userDetails);
    }
    public boolean isValidToken(String token,UserDetails userDetails){
         final String userName=extractUsername(token);
        return (userDetails.getUsername().equals(userName))&&!isTokenExpeired(token);
    }

    private boolean isTokenExpeired(String token) {
        return extractExperationDate(token).before(new Date(System.currentTimeMillis()));
    }
    public String generateRefreshToken(UserDetails userDetails) {

        String refreshToken=Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

//       this.refreshTokenService.add(refreshToken,userDetails);
        return refreshToken;
    }

    public String exractId(String token){
        return extractClaim(token,Claims::getId);
    }
    private Date extractExperationDate(String token) {
            return extractClaim(token,Claims::getExpiration);
    }
}
