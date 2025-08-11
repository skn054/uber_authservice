package com.example.uber.demo.models;

import jakarta.persistence.*;
import lombok.*;

/** this entity represents over all review of a booking*/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "booking_review")
public class Review extends BaseModel{

    @Column(nullable = false)
    private String content;

    private Double rating;

    @OneToOne
    @JoinColumn
    private Booking booking;



}

/**
 * InheritanceType.JOINED creates a table for parent a child. child will not have properties of parent. link is established by having foreign key constraint on parent entity.
 * */
