package com.example.MessengerAppp.message;

import com.example.MessengerAppp.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
private MessagerRpository messagerRpository;
@Autowired
    public MessageService(MessagerRpository messagerRpository){
        this.messagerRpository=messagerRpository;
    }
    public Message save(Message message){
     return   this.messagerRpository.save(message);
    }
    public Message findById(int id){
        return this.messagerRpository.findById(id).orElse(null);
    }
    public List<Message> findBySenderId(int senderId){
        return this.messagerRpository.findBySenderId(senderId);
    }
    public List<Message> findByRecipientId(int recipientId){
            return this.messagerRpository.findByRecipientId(recipientId);
    }
    public List<MessageDTO> conversationBetween(int user1,int user2){
   return this.messagerRpository.conversationBetween(user1,user2).stream().map(message -> {
        return Mapper.toMessageDTO(message);
    }).collect(Collectors.toList());
    }
    public List<Message> findMessageWhereUserInvolved(int userId){
        return  this.messagerRpository.findMessageWhereUserInvolved(userId);
    }


}
