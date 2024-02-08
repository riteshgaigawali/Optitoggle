package com.optitoggle.main.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    @NotEmpty(message = "Name can not be empty !!")
    private String firstName;
    @NotEmpty(message = "Name can not be empty !!")
    private String lastName;
    @Email(message = "Email address is not valid !!")
    private String emailid;
    @NotEmpty
    @Size(min = 3, max = 10, message = "Password must be min 3 charecters or max 10 charecters long !!")
    private String password;

}
