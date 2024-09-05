package com.example.MessengerAppp.message;

import com.example.MessengerAppp.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(
        origins = {
                "http://localhost:4200","http://192.168.189.46:42OO"

        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
@Controller
    @RequestMapping("/messsages")
public class MessageController {
    private MessageService messageService;
    SimpMessagingTemplate messagingTemplate;
    @Autowired
public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate){
        this.messageService=messageService;
        this.messagingTemplate=messagingTemplate;
    }
    @MessageMapping("/chat")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        this.messageService.save(Mapper.toMessage(messageDTO));
       messagingTemplate.convertAndSendToUser(
                String.valueOf(messageDTO.recipientId),
                "/topic/messages",
                messageDTO
        );


    }
    @GetMapping("/getMessagesWhereUserInvolved/{id}")
    public List<Message> findMessageWhereUserInvolved(@PathVariable int id){
        return this.messageService.findMessageWhereUserInvolved(id);
    }
    @GetMapping("/conversationBetween/{user1}/{user2}")
    public ResponseEntity<List<MessageDTO>> conversationBetween(@PathVariable(name = "user1") int user1,@PathVariable(name = "user2") int user2){
        return new ResponseEntity<>(this.messageService.conversationBetween(user1,user2), HttpStatus.OK);
    }


}
