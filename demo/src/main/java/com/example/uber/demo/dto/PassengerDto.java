package com.example.uber.demo.dto;


import com.example.entity.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private Long id;

    private String name;

    private String email;

    private String password; // encrypted password

    private Date createdAt;

    public  static PassengerDto from(Passenger passenger){
        PassengerDto passengerDto = PassengerDto.builder()
                .id(passenger.getId())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .password(passenger.getPassword())
                .createdAt(passenger.getCreatedAt())
                .build();
        return  passengerDto;
    }

}
