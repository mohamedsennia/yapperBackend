package com.example.MessengerAppp.user;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDTO {
    @NotBlank(message = "First name is required")
        private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @Email(message = "Invalid Email Format")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;
    private String password;
}
