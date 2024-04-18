package com.example.demotaskregistration.valadator;

import com.example.demotaskregistration.dto.UserDto;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

public class UserValidator {

    public static List<String> validate(UserDto dto)
    {
        List<String> errors = new ArrayList<>();

        if (dto == null)
        {
            errors.add("please add a User");
        }
        if (!StringUtils.hasLength(dto.getFirst_name()))
        {
            errors.add("add your name!");
        }
        if (!StringUtils.hasLength(dto.getEmail()))
        {
            errors.add("add your Email!");
        }
        if (!StringUtils.hasLength(dto.getPassword()))
        {
            errors.add("add your Password!");
        }
        if (!StringUtils.hasLength(dto.getIdentityNumber())){
            errors.add("add your getIdentity_number!");
        }
        return errors;
    }
}
