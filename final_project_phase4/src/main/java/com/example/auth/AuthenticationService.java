package com.example.auth;

import com.example.configuration.JwtService;
import com.example.entity.Customer;
import com.example.entity.Expert;
import com.example.exceptions.user.UserExistException;
import com.example.exceptions.user.UserNotFoundException;
import com.example.repository.AdminRepository;
import com.example.repository.CustomerRepository;
import com.example.repository.ExpertRepository;
import com.example.service.impl.EmailServiceImpl;
import com.example.utils.ConvertUrlToByteArray;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final ConvertUrlToByteArray convertUrlToByteArray;
    private final ExpertRepository expertRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailServiceImpl emailSenderService;

    public AuthenticationResponse registerCustomer(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        try {
            customerRepository.save(customer);
        } catch (DataIntegrityViolationException e) {
            throw new UserExistException("customer with given email :-- " + customer.getEmail() + " -- has already registered.");
        }
        var jwtToken = jwtService.generateToken(customer);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerExpert(Expert expert, MultipartFile file) throws IOException {
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        try {
            byte[] image = convertUrlToByteArray.converter(file);
            Expert newExpert = expertRepository.save(expert);
            String token = UUID.randomUUID().toString();

            newExpert.setImageUrl(image);
            newExpert.setToken(token);
            Expert saved = expertRepository.save(newExpert);

            emailSenderService.sendMail(saved, "http://localhost:8081/api/v1/auth/verify/expert");
        } catch (DataIntegrityViolationException e) {
            throw new UserExistException("expert with given email :-- " + expert.getEmail() + " -- has already registered.");
        }
        var jwtToken = jwtService.generateToken(expert);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticateCustomer(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var customer = customerRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("customer not found"));
        var jwtToken = jwtService.generateToken(customer);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticateExpert(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var expert = expertRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("expert not found"));
        var jwtToken = jwtService.generateToken(expert);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticateAdmin(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("admin not found"));
        var jwtToken = jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
