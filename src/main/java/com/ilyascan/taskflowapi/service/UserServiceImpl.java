package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.Security.CustomUserDetails;
import com.ilyascan.taskflowapi.dto.UserDto;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.exception.CustomException;
import com.ilyascan.taskflowapi.exception.ExceptionError;
import com.ilyascan.taskflowapi.handler.ApiResponce;
import com.ilyascan.taskflowapi.repository.UserRepository;
import com.ilyascan.taskflowapi.request.UpdateChangePassword;
import com.ilyascan.taskflowapi.request.UserUpdateRequest;
import com.ilyascan.taskflowapi.responce.UserGetMeResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository  userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public ResponseEntity<?> getMe(Authentication authentication) {
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Başarıyla kullanıcı bilgisi geldi")
                .data(toUserResponce(principal.getUser()))
                .timestamp(new Date())
                .build()
        ) ;
    }

    @Override
    public ResponseEntity<?> updateUser(UserUpdateRequest userUpdateRequest, Authentication authentication) {

        User user = authGetUser(authentication);
        boolean flag = false;
        if (isChangingField(user.getUsername(), userUpdateRequest::getUsername, user::setUsername)) flag = true;
        if (isChangingField(user.getFirstName(), userUpdateRequest::getFirstName, user::setFirstName))flag = true;
        if (isChangingField(user.getSurname(), userUpdateRequest::getSurname, user::setSurname)) flag = true;
        if (isChangingField(user.getEmail(), userUpdateRequest::getEmail, user::setEmail))  flag = true;

        if (flag){
            return ResponseEntity.ok(
                    ApiResponce.builder()
                            .success(true)
                            .data("Profil bilgileriniz başarıyla güncellendi.")
                            .timestamp(new Date())
                            .build()
            );
        }

        return ResponseEntity.ok(
                ApiResponce.builder()
                        .success(true)
                        .data("Herhangi bir değişiklik algılanmadı.")
                        .timestamp(new Date())
                        .build()
        );
    }

    @Override
    public ResponseEntity<?> changePassword(UpdateChangePassword userUpdateRequest, Authentication authentication) {
        User user = authGetUser(authentication);

        String oldPassword = userUpdateRequest.getOldPassword();
        String newPassword = userUpdateRequest.getNewPassword();
        String changePassword = userUpdateRequest.getNewAgainPassword();
        if (!newPasswordChanged(newPassword, changePassword)) {
            throw new CustomException(ExceptionError.PASSWORDS_DO_NOT_MATCH);
        }
        if (bCryptPasswordEncoder.matches(oldPassword,user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(changePassword));
            userRepository.save(user);
            return  ResponseEntity.ok(ApiResponce.builder()
                    .success(true)
                    .message("Şifre güncellendi")
                    .timestamp(new Date())
                    .build()
            );
        }else {
            throw  new CustomException(ExceptionError.INVALID_PASSWORD);
        }
    }

    @Override
    public ResponseEntity<?> deleteMe(Authentication authentication) {
        User user = authGetUser(authentication);
         userRepository.delete(user);
        return ResponseEntity.ok(ApiResponce.builder()
                .success(true)
                .message("Başarılı bir şekilde kullanıcı silindi")
                .timestamp(new Date())
                .build()
        );
    }

    private boolean newPasswordChanged(String newPassword, String chanheNewPassword) {
        return newPassword.equals(chanheNewPassword);
    }

    private User authGetUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new CustomException(ExceptionError.UNAUTHORIZED);
        }

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (user == null) {
            throw new CustomException(ExceptionError.UNAUTHORIZED);
        }
        return user;
    }

    private <T> boolean isChangingField(T field,Supplier<T> getSupplier, Consumer<T> setSupplier) {
        T newFieldValue = getSupplier.get();
        if (field == null || Objects.equals(field,newFieldValue)) {
            return  false;
        }

        updateUserSet(newFieldValue, setSupplier);
        return true;
    }

    private <T> void updateUserSet(T newField, Consumer<T> setCunsomer){
        setCunsomer.accept(newField);
    }

    private UserGetMeResponce toUserResponce(User user) {
        return UserGetMeResponce.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }
}
