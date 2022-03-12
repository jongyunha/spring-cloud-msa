package io.jongyun.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestUserDto {

    @NotNull(message = "Email is required")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "Name is required")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password must be less than 8 characters")
    private String password;
}
