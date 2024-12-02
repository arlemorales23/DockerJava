package org.arle.apirestdocker.application.port.in;

import org.arle.apirestdocker.application.dto.AuthRequest;
import org.arle.apirestdocker.application.dto.AuthResponse;
import org.arle.apirestdocker.application.dto.UserDto;

public interface AuthenticationUseCase {
    AuthResponse authenticate(AuthRequest request);
    UserDto register(AuthRequest request);
}