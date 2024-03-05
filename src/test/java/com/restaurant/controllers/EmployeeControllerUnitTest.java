package com.restaurant.controllers;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.restaurant.models.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.models.entities.Employee;
import com.restaurant.services.EmployeeService;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerUnitTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void getAllEmployees_ShouldReturnEmployees() throws Exception {
        Employee employee1 = new Employee("Test Name", Role.MANAGER, "test@test.ro", 50000.0);
        Employee employee2 = new Employee("Jane Doe", Role.CHEF, "jane@example.com", 45000.0);
        given(employeeService.getAllEmployees()).willReturn(Arrays.asList(employee1, employee2));

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Test Name")))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")));
    }

    @Test
    void getEmployeeById_ShouldReturnEmployee() throws Exception {
        Employee employee = new Employee("Test Name", Role.MANAGER, "test@test.ro", 50000.0);
        employee.setId(1L);
        given(employeeService.getEmployeeById(1L)).willReturn(Optional.of(employee));

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Name")));
    }

    @Test
    void getEmployeeById_NotFound() throws Exception {
        given(employeeService.getEmployeeById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEmployee_ShouldCreateEmployee() throws Exception {
        Employee employee = new Employee("New Employee", Role.WAITER, "new@example.com", 30000.0);
        employee.setId(1L);
        given(employeeService.createEmployee(org.mockito.ArgumentMatchers.any(Employee.class))).willReturn(employee);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Employee\", \"role\": \"WAITER\", \"contactInfo\": \"new@example.com\", \"salary\": 30000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Employee")));
    }

    @Test
    void updateEmployee_ShouldUpdateEmployee() throws Exception {
        Employee employee = new Employee("Updated Employee", Role.WAITER, "updated@example.com", 35000.0);
        employee.setId(1L);
        given(employeeService.updateEmployee(org.mockito.ArgumentMatchers.any(Employee.class))).willReturn(employee);

        mockMvc.perform(put("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Employee\", \"role\": \"WAITER\", \"contactInfo\": \"updated@example.com\", \"salary\": 35000}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Employee")));
    }

    @Test
    void updateEmployee_HandleException() throws Exception {
        given(employeeService.updateEmployee(org.mockito.ArgumentMatchers.any(Employee.class))).willThrow(new RuntimeException("Error"));

        mockMvc.perform(put("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Faulty Employee\", \"role\": \"WAITER\", \"contactInfo\": \"faulty@example.com\", \"salary\": 30000}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }
}