package com.example.employeemanagmentsystem.moudle;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employees {
    @NotEmpty(message = "ID cant be empty")
    @Size(min = 3, message = "ID length must be above 3 digits")
    private String id;
    @NotEmpty (message = "Name cant be empty")
    @Size(min = 5 , message = " Name length must be above 4 Charcters")
    @Pattern(regexp = "^[A-Za-z]+$",
            message = "Name must contain only Charcters & name cant be empty ")
    private String name;
    @NotEmpty (message = "Email cant be empty")
    @Email(message = "Email must be valid have @")
    private String email;
    @NotEmpty(message = "phone number cant be empty")
    @Size(min = 10 , max = 10 , message = "Phone number must be exactly 10 Digits")
    @Pattern(regexp = "^05.*$", message = "phonenumber must start with 05")
    private String phonenumber;
    @NotNull(message = "age Cant be null ")
    @Positive(message = "Age must be a a number above 0 ")
    private int age;
    @NotEmpty(message = "Position cant be empty")
    @Pattern(regexp = "^(?i)(supervisor|coordinator)$", message = "position must be supervisor or coordinator only")
    private String position;
    private boolean onLeave = false;
    @JsonFormat(pattern="yyyy-MM-dd")
    @PastOrPresent(message = "Date must be Past Or Present ")
    private LocalDate hireDate;
    @NotNull(message = "annualLeave must be not null")
    @Positive (message = "annualLeave must be  positive number and above 0")
    private int annualLeave;

}
