package com.example.MessengerAppp.converstation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation,Integer>{
    @Query("""
            SELECT C FROM Conversation C
            JOIN C.participants p
            where p.id=:profileId
            """)
    Page<Conversation> findAllByProfileId(@Param("profileId") int profileId, Pageable pageable);
    @Query("""
    SELECT c
    FROM Conversation c
    JOIN c.participants p
    WHERE c.type = 'A'
      AND p.id IN (:profileId1, :profileId2)
    GROUP BY c.id
    HAVING COUNT(DISTINCT p.id) = 2
""")
    Optional<Conversation> findConversationBetweenProfiles(
            @Param("profileId1") int profileId1,
            @Param("profileId2") int profileId2
    );
}
