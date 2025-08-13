package com.example.uber.demo.controller;

import com.example.uber.demo.dto.AuthRequestDto;
import com.example.uber.demo.dto.AuthResponseDto;
import com.example.uber.demo.dto.PassengerDto;
import com.example.uber.demo.dto.SignUpPassengerDto;
import com.example.uber.demo.models.Passenger;
import com.example.uber.demo.service.AuthService;
import com.example.uber.demo.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private final AuthenticationManager  authenticationManager;
    private final JwtService jwtService;
    public  AuthController(AuthService authService,AuthenticationManager authenticationManager, JwtService jwtService){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpPassengerDto signUpPassengerDto){
            PassengerDto passengerDto =  authService.signUpPassenger(signUpPassengerDto);
            return new ResponseEntity<>(passengerDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){

          Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword()));
          if(authentication.isAuthenticated()){
              String jwtToken = jwtService.createToken(authRequestDto.getEmail());
              ResponseCookie cookie = ResponseCookie.from("JwtToken",jwtToken)
                      .httpOnly(true) // http only cookie not accessible using client side javascript.
                      .secure(false)
                      .path("/")
                      .maxAge(7*24*3600)
                      .build();
              response.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
              return new ResponseEntity<>(AuthResponseDto.builder().success(true).build(),HttpStatus.OK);
          }else{
              throw new UsernameNotFoundException("User not found");
          }
    }

    @GetMapping("/validate") // fetches jwt token
    public void getToken(HttpServletRequest httpServletRequest){
        System.out.println(httpServletRequest.getCookies());

    }

}
