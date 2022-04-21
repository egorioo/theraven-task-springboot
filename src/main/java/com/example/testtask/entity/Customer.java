package com.example.testtask.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long created;

    @Column
    private Long updated;

    @Column(name = "full_name", nullable = false)
    @Size(min = 2, max = 50, message = "name must be between 2 and 50 characters including spaces")
    @NotNull(message = "name can't be null")
    private String fullName;

    @Column(unique = true)
    @Email(message = "email must match the format (2..100 chars, unique, should include exactly one @)")
    @Size(min = 2, max = 100, message = "email must be between 2 and 100 characters")
    @NotNull(message = "email can't be null")
    private String email;

    @Column()
    @Pattern(regexp = "\\+\\d{6,14}", message = "phone must match the format (6..14 chars, only digits, should start from +)")
    private String phone;

    @Column(name = "is_active")
    private boolean isActive = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isActive=" + isActive +
                '}';
    }


}
