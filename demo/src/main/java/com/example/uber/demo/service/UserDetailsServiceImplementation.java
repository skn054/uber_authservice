package com.example.uber.demo.service;

import com.example.uber.demo.config.PassengerDetails;
import com.example.uber.demo.models.Passenger;
import com.example.uber.demo.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Passenger> optionalPassenger = passengerRepository.findByEmail(username);
        if(optionalPassenger.isPresent()){
            return new PassengerDetails(optionalPassenger.get());
        }else{
            throw new UsernameNotFoundException("Cannot find the Passenger by given email");
        }
    }
}
