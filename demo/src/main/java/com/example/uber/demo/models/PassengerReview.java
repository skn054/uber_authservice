package com.example.uber.demo.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
public class PassengerReview extends Review{

    @Column(nullable = false)
    private String passengerComment;

    @Column(nullable = false)
    private String passengerRating;
}

