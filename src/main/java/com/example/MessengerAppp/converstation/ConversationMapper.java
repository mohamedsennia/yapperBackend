package com.example.MessengerAppp.converstation;

import com.example.MessengerAppp.message.MessageMapper;

public class ConversationMapper {
    public static GetConversationDTO toConversationDTO(Conversation conversation){
        return  new GetConversationDTO(conversation.getId(), MessageMapper.toMessageDTO(conversation.getMessages().getLast()));
    }
}
