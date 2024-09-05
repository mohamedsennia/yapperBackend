package com.example.MessengerAppp.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessagerRpository extends JpaRepository<Message,Integer> {
public List<Message> findBySenderId(int id);
public List<Message> findByRecipientId(int id);
@Query("SELECT m FROM Message m where m.sender.id=:id OR m.recipient.id=:id")
public List<Message> findMessageWhereUserInvolved(@Param("id") int userId);
@Query("SELECT m FROM Message m where (m.sender.id=:user1 AND m.recipient.id=:user2) OR (m.sender.id=:user2 AND m.recipient.id=:user1)")
public List<Message> conversationBetween(@Param("user1") int user1,@Param("user2") int user2);
}
