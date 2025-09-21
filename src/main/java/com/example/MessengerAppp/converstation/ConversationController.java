package com.example.MessengerAppp.converstation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/conversation")
public class ConversationController {
    private ConversationService conversationService;
    @Autowired
    public ConversationController(ConversationService conversationService){
        this.conversationService=conversationService;
    }
    @GetMapping("/{page}")
    public ResponseEntity<Page<GetConversationDTO>> getConversationsByProfile(@PathVariable(name = "page") int page){
        return new ResponseEntity<Page<GetConversationDTO>>(this.conversationService.getConversations(page), HttpStatus.OK);
    }
}
