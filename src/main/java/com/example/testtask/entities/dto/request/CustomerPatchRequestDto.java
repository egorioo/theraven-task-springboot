package com.example.testtask.entities.dto.request;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerPatchRequestDto {
    @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters including spaces")
    private String fullName;
    @Pattern(regexp = "\\+\\d{6,14}", message = "phone must match the format (6..14 chars, only digits, should start from +)")
    private String phone;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
