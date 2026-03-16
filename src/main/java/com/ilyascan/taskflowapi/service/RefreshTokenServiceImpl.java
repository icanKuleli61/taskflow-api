package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.entity.RefreshToken;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.repository.RefreshTokenRepository;
import com.ilyascan.taskflowapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(getFindByUserId(userId))
                .token(UUID.randomUUID().toString())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .build();
        return  refreshTokenRepository.save(refreshToken);
    }


    private User getFindByUserId(Long userId) {
        return userRepository.findById(UUID.fromString(userId.toString())).orElseThrow(
                () -> new RuntimeException("User not found!")
        );
    }

    private boolean isTokenValid(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new RuntimeException("Token not found!")
        );
        return refreshToken.getExpiration().before(new Date());
    }

    private boolean isRefreshTokenValid(RefreshToken refreshToken) {
        return refreshToken.getExpiration().before(new Date());
    }





}
