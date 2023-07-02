package com.example.demotaskregistration.jwt;

import com.example.demotaskregistration.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;
@Slf4j
@Service
public class UsersDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    private com.example.demotaskregistration.models.User userDetail;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside loadUserByUsername {}", username);
        userDetail = userRepository.findByEmailId(username);
        if (!Objects.isNull(username)) {
            return new User(userDetail.getEmail(),userDetail.getPassword(), new ArrayList<>());
        } else
            throw new UsernameNotFoundException("User not found");
    }

    public com.example.demotaskregistration.models.User getUserDetail() {
        return userDetail;
    }

}
