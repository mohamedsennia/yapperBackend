package com.example.MessengerAppp.converstation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConversationRepository extends JpaRepository<Integer,Conversation>{
    @Query("""
            SELECT C FROM Conversation C
            JOIN C.participants p
            where p.id=:profileId
            """)
    Page<Conversation> findAllByProfileId(@Param("profileId") int profileId, Pageable pageable);
}
