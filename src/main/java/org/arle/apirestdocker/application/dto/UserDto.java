package org.arle.apirestdocker.application.dto;

import lombok.Builder;
import lombok.Data;
import org.arle.apirestdocker.domain.model.Role;

@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
