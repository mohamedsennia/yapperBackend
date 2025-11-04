package com.example.MessengerAppp.conifg;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MessageLoggingInterceptor implements ChannelInterceptor {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    public MessageLoggingInterceptor(JwtService jwtService,UserDetailsService userDetailsService){
        this.jwtService=jwtService;
        this.userDetailsService=userDetailsService;
    }
    @Override
    public Message<?> preSend(Message<?> message,MessageChannel channel){
        StompHeaderAccessor accessor= MessageHeaderAccessor.getAccessor(message,StompHeaderAccessor.class);
        if(accessor != null &&StompCommand.CONNECT.equals(accessor.getCommand())){
            String token=accessor.getFirstNativeHeader("Authorization");
            if(token!=null&&token.startsWith("Bearer ")){
                token=token.substring(7);
                final String userEmail=jwtService.extractUsername(token);
                UserDetails userDetails=userDetailsService.loadUserByUsername(userEmail);
                if(jwtService.isValidToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(userEmail,null,null);
                    accessor.setUser(auth);
                }else {
                    throw new IllegalArgumentException("Invalid JWT token");
                }
            }else {
                throw new IllegalArgumentException("Missing JWT token");
            }
        }
        return message;
    }
    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {

    }

    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {

    }
}
