package com.example.MessengerAppp.profile;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepositoty  extends JpaRepository<Profile,Integer> {
Optional<Profile> findByOwnerEmail(String email);
@Query("Select p FROM Profile p Where LOWER(p.profileName) LIKE LOWER(CONCAT('%', :keyWord, '%') or LOWER(p.owner.email) Like LOWER(CONCAT('%', :keyWord, '%')")
List<Profile> searchProfile(String keyWord);
}
