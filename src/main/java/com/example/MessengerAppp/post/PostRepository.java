package com.example.MessengerAppp.post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface PostRepository  extends JpaRepository<Post,Integer> {
    Page<Post> findByProfileId(int id,Pageable pageable);
    @Query("SELECT p FROM Post p where p.profile.owner.id IN( SELECT f.id From User u JOIN u.following f where u.email= :email)")
    Page<Post> getFeed(@Param("email") String email, Pageable pageable);
}
