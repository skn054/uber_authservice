package com.example.uber.demo.service;

import com.example.uber.demo.dto.PassengerDto;
import com.example.uber.demo.dto.SignUpPassengerDto;
import com.example.uber.demo.models.Passenger;
import com.example.uber.demo.repository.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public PassengerDto signUpPassenger(SignUpPassengerDto signUpPassengerDto){
        Passenger passenger = Passenger.builder()
                .name(signUpPassengerDto.getName())
                .email(signUpPassengerDto.getEmail())
                .password(bCryptPasswordEncoder.encode(signUpPassengerDto.getPassword()))
                .build();
        Passenger createdPassenger = passengerRepository.save(passenger);
        return PassengerDto.from(createdPassenger);
    }
}
