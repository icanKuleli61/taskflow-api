package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.entity.RefreshToken;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.exception.CustomException;
import com.ilyascan.taskflowapi.exception.ExceptionError;
import com.ilyascan.taskflowapi.repository.RefreshTokenRepository;
import com.ilyascan.taskflowapi.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshToken createRefreshToken(UUID userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(getFindByUserId(userId))
                .token(UUID.randomUUID().toString())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .build();
        return  refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken refreshToken(String oldToken) {
        UUID userId = getUserExctract(oldToken).getUserId();
        RefreshToken token = isTokenValid(oldToken,userId);
        token.setRevoked(true);
        refreshTokenRepository.save(token);
        return createRefreshToken(userId);
    }

    @Override
    public User getUserExctract(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new CustomException(ExceptionError.INVALID_REFRESH_TOKEN)
        );
        return refreshToken.getUser();
    }




    private User getFindByUserId(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ExceptionError.USER_NOT_FOUND)
        );
    }

    private RefreshToken isTokenValid(String token, UUID userId) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new CustomException(ExceptionError.INVALID_REFRESH_TOKEN)
        );
        isTokenCheckUsernameValid(refreshToken, userId);

        if (!isRefreshTokenValid(refreshToken,userId)){
            throw new CustomException(ExceptionError.INVALID_REFRESH_TOKEN);
        }
        return refreshToken;
    }

    private void isTokenCheckUsernameValid(RefreshToken refreshToken, UUID userId) {
        User findByUserId = getFindByUserId(userId);
        if (!refreshToken.getUser().getEmail().equals(findByUserId.getEmail())) {
            throw new CustomException(ExceptionError.INVALID_REFRESH_TOKEN);
        }
    }

    private boolean isRefreshTokenValid(RefreshToken refreshToken,UUID userId) {
        if (refreshToken.isRevoked()){
            isRekovedTrueLayaut(userId);
            return false;
        }
        return refreshToken.getExpiration().after(new Date());
    }

    @Override
    public void isRekovedTrueLayaut(UUID userId) {
        List<RefreshToken> byUserUserId = refreshTokenRepository.findByUserUserId(userId);
        for (RefreshToken refreshToken : byUserUserId) {
            refreshToken.setRevoked(true);
        }
    }









}
