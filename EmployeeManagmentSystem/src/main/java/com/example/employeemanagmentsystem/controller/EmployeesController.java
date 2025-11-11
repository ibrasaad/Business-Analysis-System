package com.example.employeemanagmentsystem.controller;

import com.example.employeemanagmentsystem.Api.ApiResponse;
import com.example.employeemanagmentsystem.moudle.Employees;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeesController {
    public ArrayList<Employees> employeeslist = new ArrayList<>();


    @GetMapping("/get")
    public ArrayList<Employees> getEmployeeslist() {
        return employeeslist;
    }

    @PostMapping("/add")
    public ResponseEntity<?> adduser(@RequestBody @Valid Employees employees, Errors errors) {
        if (errors.hasErrors()) {
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiResponse(message));

        }
        employeeslist.add(employees);
        return ResponseEntity.status(200).body("user add succfulley ");
    }

    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateuser(@PathVariable int index, @RequestBody @Valid Employees employees) {
        employeeslist.set(index, employees);
        return ResponseEntity.status(200).body(new ApiResponse("Updated succfully"));
    }


    @DeleteMapping("/remove/{index}")
    ResponseEntity<?> deleteuser(@PathVariable String index) {
        if (employeeslist.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiResponse("arraylist is empty"));
        }
        for (Employees e : employeeslist) {
            if (e.getId().equalsIgnoreCase(index)) {
                employeeslist.remove(index);
            }
        }

        return ResponseEntity.status(400).body(new ApiResponse("user is already not exists in the system"));

    }

    @GetMapping("/findbyposition/{byposition}")
    ResponseEntity<?> SearchByPosition(@PathVariable String byposition) {
        ArrayList<Employees> list = new ArrayList<>();
        for (Employees e : employeeslist) {
            if (!e.getPosition().equalsIgnoreCase(byposition)) {
                return ResponseEntity.status(400).body(new ApiResponse("position must be supervisor or coordinator "));
            }
            list.add(e);

        }
        return ResponseEntity.status(200).body(list);

    }

    @GetMapping("/employeesbyages/{min}/{max}")
    ResponseEntity<?> getEmployeesByAge(@PathVariable int min, @PathVariable int max) {
        ArrayList<Employees> ageslist = new ArrayList<>();
        if (min <= 0 || max <= 0 || max < min) {
            return ResponseEntity.status(400).body("please but a valid Ages");
        }
        for (Employees e : employeeslist) {
            if (e.getAge() >= min && e.getAge() <= max) {
                ageslist.add(e);
            }
        }
        return ResponseEntity.status(200).body(ageslist);


    }

    @PutMapping("/annualLeave/{index}")
    ResponseEntity<?> applyForAnnualLeave(@PathVariable String index) {
        for (Employees e : employeeslist) {
            if (e.getId().equalsIgnoreCase(index)) {
                if (!e.isOnLeave() && e.getAnnualLeave() >= 1) {
                    e.setOnLeave(true);
                    e.setAnnualLeave(e.getAnnualLeave() - 1);
                    return ResponseEntity.status(200).body(e);
                } else {
                    return ResponseEntity.status(400).body("Employee Dosent have enough days ");
                }
            }
        }
        return ResponseEntity.status(400).body("Employee Dosent exists");

    }

    @GetMapping("/Emp-no-annual")
    ResponseEntity<?> getEmployeeswithnoanuallyears() {
        ArrayList<Employees> noAnnuallist = new ArrayList<>();
        for (Employees e : employeeslist) {
            if (e.getAnnualLeave() == 0) {
                noAnnuallist.add(e);
            }
        }
        return ResponseEntity.status(200).body(noAnnuallist);
    }

    @PutMapping("/promote-emp/{empid}/{sup}")
    ResponseEntity<?> promoteEmployee(@PathVariable String sup, @PathVariable String empid) {
        Employees emp = null;
        Employees spervisor = null;
        for (Employees e : employeeslist) {
            if (e.getId().equalsIgnoreCase(empid)) {
                emp = e;
            }
            if (e.getId().equalsIgnoreCase(sup)) {
                spervisor = e;
            }
        }

        if (!spervisor.getPosition().equalsIgnoreCase("supervisor")) {
            return ResponseEntity.status(400).body("Requester is not a supervisor");
        }
        if (emp.getAge() < 30) {
            return ResponseEntity.status(400).body("Employee must be at least 30 years old");
        }
        if (emp.isOnLeave()) {
            return ResponseEntity.status(400).body("Employee is currently on leave");
        }
        if (emp.getPosition().equalsIgnoreCase("supervisor")) {
            return ResponseEntity.status(400).body("Employee already a supervisor");
        }
        emp.setPosition("supervisor");


        return ResponseEntity.status(200).body(emp);


        }


    }

