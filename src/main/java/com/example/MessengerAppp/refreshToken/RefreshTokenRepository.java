package com.example.MessengerAppp.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;



public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
}
