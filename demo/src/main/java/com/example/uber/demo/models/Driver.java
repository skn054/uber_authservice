package com.example.uber.demo.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver extends BaseModel{
    private String name;
    @Column(nullable = false,unique = true)
    private String licenseNumber;
    @OneToMany(mappedBy = "driver")
    // fetch type missing
    List<Booking> bookingList = new ArrayList<>();
}
