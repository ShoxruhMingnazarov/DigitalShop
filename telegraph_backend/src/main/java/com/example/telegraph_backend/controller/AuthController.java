package com.example.telegraph_backend.controller;

import com.example.telegraph_backend.dto.LoginDto;
import com.example.telegraph_backend.dto.UserDto;
import com.example.telegraph_backend.dto.response.JwtResponse;
import com.example.telegraph_backend.entity.UserEntity;
import com.example.telegraph_backend.entity.UserRole;
import com.example.telegraph_backend.exception.RequestValidationException;
import com.example.telegraph_backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserEntity> signUp(
            @Valid @RequestBody UserDto userDto,
            BindingResult bindingResult
            ) throws RequestValidationException {

        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return ResponseEntity.ok(userService.save(userDto));
    }


    @GetMapping("/sign-in")
    public ResponseEntity<JwtResponse> signIn(
            @RequestBody LoginDto loginDto
    ){
        return ResponseEntity.ok(userService.login(loginDto));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshAccessToken(
            Principal principal
    ){
        return ResponseEntity.ok(userService.getNewAccessToken(principal));
    }
}
