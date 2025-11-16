package com.example.MessengerAppp.profile;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepositoty  extends JpaRepository<Profile,Integer> {
Optional<Profile> findByOwnerEmail(String email);
    @Query("""
    SELECT p
    FROM Profile p
    WHERE LOWER(p.profileName) LIKE LOWER(CONCAT('%', :keyWord, '%'))
       OR LOWER(p.owner.email) LIKE LOWER(CONCAT('%', :keyWord, '%'))
""")
List<Profile> searchProfile(String keyWord);
}
