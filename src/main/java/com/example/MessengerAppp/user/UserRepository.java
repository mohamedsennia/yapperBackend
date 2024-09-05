package com.example.MessengerAppp.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
   Optional<User> findByEmail(String email);
   @Query("SELECT u FROM User u WHERE (u.firstName LIKE %:name% OR u.lastName LIKE %:name%) AND u.id!=:excludedUserId")
   List<User> findByFirstnameOrLastnameContaining(@Param("name") String name,@Param("excludedUserId") int excludedUserId);
   List<User> findByIdNot(int id);
   @Query("SELECT u FROM User u " +
           "JOIN Message m ON (m.sender.id = u.id OR m.recipient.id = u.id) " +
           "WHERE (m.sender.id = :userId OR m.recipient.id = :userId) " +
           "AND u.id <> :userId " +
           "GROUP BY u.id " +
           "ORDER BY MAX(m.time) DESC")
   List<User> findUsersInConversationWith(@Param("userId") int userId);
}
