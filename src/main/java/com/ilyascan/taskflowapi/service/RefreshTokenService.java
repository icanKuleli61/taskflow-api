package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.entity.RefreshToken;
import com.ilyascan.taskflowapi.entity.User;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(UUID userId);

    RefreshToken refreshToken(String oldToken);

    void isRekovedTrueLayaut(UUID userId);

    User getUserExctract(String token);


}
