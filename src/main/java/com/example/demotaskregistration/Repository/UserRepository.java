package com.example.demotaskregistration.Repository;

import com.example.demotaskregistration.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email=:email")
    User findByEmailId(@Param("email") String email);

    boolean existsByEmail(String email);

//    User findUserByIdentityNumber(@Param("identityNumber") String idendityNumber);
    @Query(value = "SELECT COUNT(*) > 0 FROM users WHERE identity_number = :identityNumber", nativeQuery = true)
    BigInteger existsByIdentityNumber(@Param("identityNumber") String identityNumber);


}
