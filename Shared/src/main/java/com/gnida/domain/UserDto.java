package com.gnida.domain;

import com.gnida.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    Integer user_id;
    String login;
    UserRole role;
    String firstName;
    String lastName;

}
