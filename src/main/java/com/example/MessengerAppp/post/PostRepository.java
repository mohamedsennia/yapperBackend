package com.example.MessengerAppp.post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface PostRepository  extends JpaRepository<Post,Integer> {
    Page<Post> findByProfileIdAndType(int id,PostType postType,Pageable pageable);
    @Query("""
    SELECT p 
    FROM Post p
    WHERE p.profile.id IN (
        SELECT f.id 
        FROM Profile pr 
        JOIN pr.following f 
        WHERE pr.id = :profileId
    )
    AND p.type = 'Post'
""")    Page<Post> getFeed(@Param("profileId") int profileId, Pageable pageable);
    Page<Post> findByTypeAndParentId(PostType postType,int parentId,Pageable pageable);
}
