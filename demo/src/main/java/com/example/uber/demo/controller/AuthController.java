package com.example.uber.demo.controller;

import com.example.uber.demo.dto.PassengerDto;
import com.example.uber.demo.dto.SignUpPassengerDto;
import com.example.uber.demo.models.Passenger;
import com.example.uber.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;

    public  AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpPassengerDto signUpPassengerDto){
            PassengerDto passengerDto =  authService.signUpPassenger(signUpPassengerDto);
            return new ResponseEntity<>(passengerDto, HttpStatus.CREATED);
    }
}
