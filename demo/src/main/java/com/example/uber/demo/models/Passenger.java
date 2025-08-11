package com.example.uber.demo.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Passenger extends BaseModel{


    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;  // how to check if email satisfies certain condition

    @Column(nullable = false)
    private String password;  // how to check if password satisfies certain conditions
    @OneToMany(mappedBy = "passenger")
    private List<Booking> bookingList = new ArrayList<>();
}
