package com.example.testtask.entities.dto.request;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerRequestDto {

    @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters including spaces")
    @NotNull(message = "name can't be null")
    private String fullName;

    @Email(message = "email must match the format (2..100 chars, unique, should include exactly one @)")
    @Size(min = 2, max = 100, message = "email must be between 2 and 100 characters")
    @NotNull(message = "email can't be null")
    private String email;

    @Pattern(regexp = "\\+\\d{6,14}", message = "phone must match the format (6..14 chars, only digits, should start from +)")
    private String phone;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "CustomerRequestDto{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
