package com.example.demotaskregistration.dto;

import com.example.demotaskregistration.models.User;
import lombok.*;

@Builder
@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String first_name;
    private String email;
    private String password;
    private String role;
    private String identityNumber;

    public static UserDto fromEntity(User user)
    {
        if (user == null) {return null;}

        return UserDto.builder()
                .id(user.getId())
                .first_name(user.getFirst_name())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .identityNumber(user.getIdentityNumber())
                .build();
    }

    public static User toEntity(UserDto userDto)
    {
        if (userDto == null) {return null;}

        User user = new User();
        user.setId(userDto.getId());
        user.setFirst_name(userDto.getFirst_name());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setRole(userDto.getRole());
        user.setIdentityNumber(userDto.getIdentityNumber());

        return user;
    }
}
