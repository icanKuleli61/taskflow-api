package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.Security.CustomUserDetails;
import com.ilyascan.taskflowapi.Security.JwtService;
import com.ilyascan.taskflowapi.dto.UserDto;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.repository.AuthRepository;
import com.ilyascan.taskflowapi.request.AuthLoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService  jwtService;

    public AuthServiceImpl(AuthRepository authRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, JwtService jwtService) {
        this.authRepository = authRepository;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
                )
        );

        CustomUserDetails principal = (CustomUserDetails) authenticate.getPrincipal();

        assert principal != null;
        String token = jwtService.generateToken(principal);

        return ResponseEntity.ok(token);
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
