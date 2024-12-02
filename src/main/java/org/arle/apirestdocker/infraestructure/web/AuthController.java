package org.arle.apirestdocker.infraestructure.web;


import lombok.RequiredArgsConstructor;
import org.arle.apirestdocker.application.dto.AuthRequest;
import org.arle.apirestdocker.application.dto.AuthResponse;
import org.arle.apirestdocker.application.dto.UserDto;
import org.arle.apirestdocker.application.port.in.AuthenticationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")  // Añadido para permitir peticiones cross-origin
public class AuthController {

    private final AuthenticationUseCase authenticationUseCase;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationUseCase.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationUseCase.authenticate(request));
    }

    // Endpoint de prueba para verificar que el controlador está funcionando
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth controller is working!");
    }
}