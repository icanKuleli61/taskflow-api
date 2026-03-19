package com.ilyascan.taskflowapi.service;

import com.ilyascan.taskflowapi.Security.CustomUserDetails;
import com.ilyascan.taskflowapi.dto.UserDto;
import com.ilyascan.taskflowapi.entity.User;
import com.ilyascan.taskflowapi.repository.UserRepository;
import com.ilyascan.taskflowapi.request.UpdateChangePassword;
import com.ilyascan.taskflowapi.request.UserUpdateRequest;
import com.ilyascan.taskflowapi.responce.UserGetMeResponce;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        return ResponseEntity.ok(toUserResponce(principal.getUser())) ;
    }

    @Override
    public ResponseEntity<?> updateUser(UserUpdateRequest userUpdateRequest, Authentication authentication) {

        User user = authGetUser(authentication);
        StringBuilder builder = new StringBuilder();
        String field = isChangingField("İsim", user.getUsername(), userUpdateRequest::getUsername, user::setUsername);
        String field1 = isChangingField("Soyisim", user.getFirstName(), userUpdateRequest::getFirstName, user::setFirstName);
        String field2 = isChangingField("İsim", user.getSurname(), userUpdateRequest::getSurname, user::setSurname);
        String field3 = isChangingField("Email", user.getEmail(), userUpdateRequest::getEmail, user::setEmail);
        builder.append(field).append(" ").append(field1).append(" ").append(field2).append(" ").append(field3);
        String message = "";
        if (builder.length() > 5) {
            builder.append(" Alanları başarıyla değişmiştir");
        }else {
            builder.delete(0, builder.length());
            builder.append("Verilerinizde değişen bir şey yoktur.");
        }
        return ResponseEntity.ok(builder.toString());
    }

    @Override
    public ResponseEntity<?> changePassword(UpdateChangePassword userUpdateRequest, Authentication authentication) {
        User user = authGetUser(authentication);

        String oldPassword = userUpdateRequest.getOldPassword();
        String newPassword = userUpdateRequest.getNewPassword();
        String changePassword = userUpdateRequest.getNewAgainPassword();
        if (!newPasswordChanged(newPassword, changePassword)) {
            throw new RuntimeException("Şifreler Uyuşmuyor");
        }
        if (bCryptPasswordEncoder.matches(oldPassword,user.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(changePassword));
            userRepository.save(user);
            return  ResponseEntity.ok("şifre güncellendi");
        }else {
            return ResponseEntity.ok("Mevcut şifren yanlış");
        }
    }

    @Override
    public ResponseEntity<?> deleteMe(Authentication authentication) {
        User user = authGetUser(authentication);
         userRepository.delete(user);
        return ResponseEntity.ok("Başarılı bir şekilde kullanıcı silindi");
    }

    private boolean newPasswordChanged(String newPassword, String chanheNewPassword) {
        return newPassword.equals(chanheNewPassword);
    }

    private User authGetUser(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("Kimlik doğrulama bilgisi bulunamadı.");
        }

        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        User user = principal.getUser();
        if (user == null) {
            throw new RuntimeException("Kullanıcı nesnesi Principal içinde bulunamadı.");
        }

        return user;
    }

    private <T> String isChangingField(String mesasge,T field,Supplier<T> getSupplier, Consumer<T> setSupplier) {
        T newFieldValue = getSupplier.get();
        if (field == null || Objects.equals(field,newFieldValue)) {
            return  "";
        }

        updateUserSet(newFieldValue, setSupplier);
        return mesasge;
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
