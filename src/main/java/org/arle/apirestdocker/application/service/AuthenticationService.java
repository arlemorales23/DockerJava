package org.arle.apirestdocker.application.service;


import lombok.RequiredArgsConstructor;
import org.arle.apirestdocker.application.dto.AuthRequest;
import org.arle.apirestdocker.application.dto.AuthResponse;
import org.arle.apirestdocker.application.dto.UserDto;
import org.arle.apirestdocker.application.port.in.AuthenticationUseCase;
import org.arle.apirestdocker.domain.model.Role;
import org.arle.apirestdocker.infraestructure.persistence.JpaUserRepository;
import org.arle.apirestdocker.infraestructure.persistence.UserJpaEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {

    private final JpaUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var user = repository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken((UserDetails) user);

        return AuthResponse.builder()
                .token(jwtToken)
                .user(mapToUserDto(user))
                .build();
    }

    @Override
    @Transactional
    public UserDto register(AuthRequest request) {
        if (repository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        var user = UserJpaEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .build();

        var savedUser = repository.save(user);
        return mapToUserDto(savedUser);
    }

    private UserDto mapToUserDto(UserJpaEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}