package com.example.uber.demo.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpPassengerDto {

    // part of spring validation package. @Valid tag is used to check validation.
    /** public ResponseEntity<Passenger> signUp(@Valid @RequestBody SignupPassengerDto requestDto) */
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 32, message = "Password must be between 8 and 32 characters")
    // You could add a @Pattern for complexity if needed
    private String password;




}
