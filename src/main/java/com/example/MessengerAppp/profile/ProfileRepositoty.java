package com.example.MessengerAppp.profile;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepositoty  extends JpaRepository<Profile,Integer> {
Optional<Profile> findByOwnerEmail(String email);
}
