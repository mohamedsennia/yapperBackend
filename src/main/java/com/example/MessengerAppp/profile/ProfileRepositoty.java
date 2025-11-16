package com.example.MessengerAppp.profile;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProfileRepositoty  extends JpaRepository<Profile,Integer> {
Optional<Profile> findByOwnerEmail(String email);
@Query("Select p FROM Profile p Where p.profileName LIKE %:keyWord% or p.owner.email Like %:keyWord%")
List<Profile> searchProfile(String keyWord);
}
