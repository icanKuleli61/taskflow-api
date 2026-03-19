package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.Security.CustomUserDetails;
import com.ilyascan.taskflowapi.Security.JwtService;
import com.ilyascan.taskflowapi.dto.UserDto;
import com.ilyascan.taskflowapi.entity.RefreshToken;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.repository.AuthRepository;
import com.ilyascan.taskflowapi.repository.UserRepository;
import com.ilyascan.taskflowapi.request.AuthLoginRequest;
import com.ilyascan.taskflowapi.responce.JwtResponce;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final JwtService  jwtService;

    public AuthServiceImpl(AuthRepository authRepository, AuthenticationManager authenticationManager,
                           BCryptPasswordEncoder bCryptPasswordEncoder, RefreshTokenService refreshTokenService, UserRepository userRepository, JwtService jwtService) {
        this.authRepository = authRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    @Override
    public ResponseEntity<?> registerUser(UserDto userDto) {
        User entity = toEntity(userDto);
        authRepository.save(entity);
        return ResponseEntity.ok(entity.getSurname()+" Kullanıcı başarılı bir şekilde kayıt oldu.");
    }

    @Override
    public ResponseEntity<?> login(AuthLoginRequest authLoginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        authLoginRequest.getEmail(),
                        authLoginRequest.password
                ));
        CustomUserDetails principal = (CustomUserDetails) authenticate.getPrincipal();

        assert principal != null;
        String token = jwtService.generateToken(principal);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(getUser(principal.getUsername()).getUserId());

        return ResponseEntity.ok(
                JwtResponce.builder()
                        .accessToken(token)
                        .refreshToken(refreshToken.getToken())
                        .build()
        );
    }


    @Override
    public ResponseEntity<?> logout(UUID userId) {
        refreshTokenService.isRekovedTrueLayaut(userId);
        return ResponseEntity.ok("Başarılı bir şekilde çıkış yapıldı");
    }

    @Override
    public ResponseEntity<?> refreshAccesToken(String oldToken) {
        RefreshToken refreshToken = refreshTokenService.refreshToken(oldToken);
        User userExctract = refreshTokenService.getUserExctract(refreshToken.getToken());
        CustomUserDetails customUserDetails = new CustomUserDetails(userExctract);
        String accesToken = jwtService.generateToken(customUserDetails);
        return ResponseEntity.ok(JwtResponce.builder().accessToken(accesToken)
                .refreshToken(refreshToken.getToken())
                .build());
    }


    private User getUser(String username) {
        return userRepository.findByEmail(username);
    }


    private User toEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .build();
    }
}
