package com.example.telegraph_backend.service;

import com.example.telegraph_backend.dto.LoginDto;
import com.example.telegraph_backend.dto.UserDto;
import com.example.telegraph_backend.dto.response.JwtResponse;
import com.example.telegraph_backend.entity.UserEntity;
import com.example.telegraph_backend.entity.UserRole;
import com.example.telegraph_backend.exception.AuthenticationFailedException;
import com.example.telegraph_backend.exception.DataNotFoundException;
import com.example.telegraph_backend.exception.UniqueObjectException;
import com.example.telegraph_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements BaseService<UserEntity, UserDto> {


    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserEntity save(UserDto userDto) {
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(List.of(UserRole.USER));
        return userRepository.save(userEntity);
    }


    @Override
    public UserEntity update(UserDto userDto, UUID id ) {
        UserEntity userEntitiesById = userRepository.findUserEntitiesById(id);
        if(!userDto.getUsername().isEmpty()){
            userEntitiesById.setUsername(userDto.getUsername());
        }
        if(!userDto.getPassword().isEmpty()){
            userEntitiesById.setPassword(userDto.getPassword());
        }
        if (!userDto.getName().isEmpty()) {
            userEntitiesById.setName(userDto.getName());
        }
        userEntitiesById.setUpdateDate(LocalDateTime.now());
        return userRepository.save(userEntitiesById);
    }

    @Override
    public void deleteById(UUID id) {
       userRepository.deleteById(id);
    }

    @Override
    public UserEntity getById(UUID id) {
        return userRepository.getById(id);
    }

    public JwtResponse signIn(String username, String password)  {
        UserEntity userEntitiesByUsername = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new AuthenticationFailedException("incorrect username or password"));
         String accessToken = jwtService.generateAccessToken(userEntitiesByUsername);
        if (userEntitiesByUsername.getPassword().equals(password)){
            return JwtResponse.builder().accessToken(accessToken).build();
        }
        throw new AuthenticationFailedException("Invalid username or password");
    }

    public JwtResponse login(LoginDto loginDto) {
        UserEntity userEntity = userRepository.findUserEntityByUsername(loginDto.getUsername()).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        if(passwordEncoder.matches(loginDto.getPassword(),userEntity.getPassword())) {
            String s = jwtService.generateAccessToken(userEntity);
            return JwtResponse.builder().accessToken(s).build();
        }
        throw new DataNotFoundException("User not found!");
    }

    public JwtResponse getNewAccessToken(Principal principal){
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName())
                .orElseThrow(() ->new DataNotFoundException("user not found"));
        String accessToken = jwtService.generateAccessToken(userEntity);
        return JwtResponse.builder().accessToken(accessToken).build();
    }

//    public Boolean blockUser(UUID userId) {
//        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
//                () -> new DataNotFoundException("user not found")
//        );
//        userEntity.setHasBlocked(true);
//        return true;
//    }
//
//    public Boolean unblockUser(UUID userId) {
//        UserEntity userEntity = userRepository.findById(userId).orElseThrow(
//                () -> new DataNotFoundException("user not found")
//        );
//        userEntity.setHasBlocked(false);
//        return true;
//    }
}
