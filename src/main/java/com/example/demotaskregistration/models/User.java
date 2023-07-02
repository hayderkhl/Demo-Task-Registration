package com.example.demotaskregistration.models;

import com.example.demotaskregistration.token.Token;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        @Column(name = "first_name")
        private String first_name;

        @Email(message = "Invalid email format")
        @Column(name = "email")
        private String email;

        @Column(name = "password")
        private String password;

        @Column(name = "role")
        private String role;

        @OneToMany(mappedBy = "user")
        private List<Token> tokens;
}
