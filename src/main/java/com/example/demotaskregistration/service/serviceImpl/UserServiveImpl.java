package com.example.demotaskregistration.service.serviceImpl;

import com.example.demotaskregistration.Repository.UserRepository;
import com.example.demotaskregistration.dto.UserDto;
import com.example.demotaskregistration.exception.EntityNotFoundException;
import com.example.demotaskregistration.exception.ErrorCodes;
import com.example.demotaskregistration.exception.InvalidEntityException;
import com.example.demotaskregistration.exception.UnauthorizedException;
import com.example.demotaskregistration.jwt.JwtFilter;
import com.example.demotaskregistration.jwt.JwtUtil;
import com.example.demotaskregistration.jwt.UsersDetailsService;
import com.example.demotaskregistration.models.User;
import com.example.demotaskregistration.service.UserService;
import com.example.demotaskregistration.token.Token;
import com.example.demotaskregistration.token.TokenRepository;
import com.example.demotaskregistration.token.TokenType;
import com.example.demotaskregistration.valadator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiveImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UsersDetailsService usersDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public ResponseEntity<String> register(UserDto dto) {
        List<String> errors = UserValidator.validate(dto);
        if (!errors.isEmpty())
        {
            log.error("Useer is not valid", dto);
            throw new InvalidEntityException("this customer is not valid", ErrorCodes.USER_NOT_VALID);
        }

        if(isEmailExists(dto.getEmail()) == true)
        {
            log.error("email already exist", dto);
            throw new InvalidEntityException("email already exist", ErrorCodes.EMAIL_ALREADY_EXIST);
        }

        dto.setRole("user");
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(dto.getPassword());
//        dto.setPassword(encodedPassword);

//        UserDto.fromEntity(userRepository.save(
//                UserDto.toEntity(dto)));
        userRepository.save(UserDto.toEntity(dto));

        return new ResponseEntity<String>("{\"message\":\"" + "User successfully registered "+"\"}", HttpStatus.OK);

    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );

            if(auth.isAuthenticated()) {

                String jwttoken = jwtUtil.generateToken(usersDetailsService.getUserDetail().getEmail(),
                        usersDetailsService.getUserDetail().getRole());

                User user = userRepository.findByEmailId(requestMap.get("email"));
                revokeAllUserTokens(user);

                var token = Token.builder()
                        .user(user)
                        .token(jwttoken)
                        .tokenType(TokenType.BEARER)
                        .revoked(false)
                        .expired(false)
                        .build();

                tokenRepository.save(token);

                return new ResponseEntity<String>("{\"token\":\""+jwttoken+"\"}", HttpStatus.OK);
            }

        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials"+"\"}",HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<UserDto> findAll() {

        if (jwtFilter.isAdmin()) {
            return userRepository.findAll().stream()
                    .map(UserDto::fromEntity)
                    .collect(Collectors.toList());
        }
        else throw new UnauthorizedException("Only for ADMIN", ErrorCodes.NOT_AUTHORIZED);
    }

    @Override
    public UserDto findById(Integer id) {

        if(jwtFilter.isAdmin()) {

            if(id == null) {
                log.error("user is null");
                return null;
            }
            Optional<User> user = userRepository.findById(id);
            return Optional.of(UserDto.fromEntity(user.get()))
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No user with this ID"+ id + " found in DB"
                            , ErrorCodes.USER_NOT_FOUND)
                    );
        }
        else throw new UnauthorizedException("Only for ADMIN", ErrorCodes.NOT_AUTHORIZED);
    }


    public boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    private void revokeAllUserTokens(User user)
    {
        //we need only one token valid we gonna make the others expired and revoked
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
