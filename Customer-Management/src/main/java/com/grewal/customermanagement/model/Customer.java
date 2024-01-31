package com.grewal.customermanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import org.hibernate.annotations.Type;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

@Entity
@Table(name = "customer_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;




    @NotBlank(message = "First name is required")
    @Length(max = 255, message = "First name cannot exceed 255 characters")


    private String firstName;

    @NotBlank(message = "Last name is required")
    @Length(max = 255, message = "Last name cannot exceed 255 characters")


    private String lastName;

    @NotBlank(message = "Street is required")
    @Length(max = 255, message = "Street cannot exceed 255 characters")

    private String street;

    @NotBlank(message = "Address is required")
    @Length(max = 255, message = "Address cannot exceed 255 characters")

    private String address;


    @NotBlank(message = "City is required")
    @Length(max = 255, message = "City cannot exceed 255 characters")

    private String city;

    @NotBlank(message = "State is required")
    @Length(max = 255, message = "State cannot exceed 255 characters")

    private String state;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Length(max = 255, message = "Email cannot exceed 255 characters")

    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]+$", message = "Invalid phone number format")

    private String phone;

}
