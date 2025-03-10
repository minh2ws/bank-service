package com.minhtn.bankservice.model.user;

import com.minhtn.bankservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Lombok annotation to generate getter and setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String role;

    public static UserDTO from(User userEntity) {
        return UserDTO.builder()
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .build();
    }
}
