package org.arle.apirestdocker.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private boolean enabled;
}
