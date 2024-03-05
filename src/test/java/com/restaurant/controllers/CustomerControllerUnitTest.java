package com.restaurant.controllers;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.models.entities.Customer;
import com.restaurant.services.CustomerService;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerUnitTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    void getAllCustomers_ShouldReturnCustomers() throws Exception {
        Customer customer1 = new Customer("Test Name", "test@test.ro", "1234567890");
        Customer customer2 = new Customer("Jane Doe", "jane@example.com", "0987654321");
        given(customerService.getAllCustomers()).willReturn(Arrays.asList(customer1, customer2));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Test Name")))
                .andExpect(jsonPath("$[1].name", is("Jane Doe")));
    }

    @Test
    void getCustomerById_ShouldReturnCustomer() throws Exception {
        Customer customer = new Customer("Test Name", "test@test.ro", "1234567890");
        customer.setId(1L);
        given(customerService.getCustomerById(1L)).willReturn(Optional.of(customer));

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test Name")));
    }

    @Test
    void getCustomerById_NotFound() throws Exception {
        given(customerService.getCustomerById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addCustomer_ShouldCreateCustomer() throws Exception {
        Customer customer = new Customer("New Customer", "newcustomer@example.com", "1234567890");
        customer.setId(1L);
        given(customerService.addCustomer(org.mockito.ArgumentMatchers.any(Customer.class))).willReturn(customer);

        mockMvc.perform(post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Customer\", \"email\": \"newcustomer@example.com\", \"phoneNumber\": \"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Customer")));
    }

    @Test
    void updateCustomer_ShouldUpdateCustomer() throws Exception {
        Customer customer = new Customer("Updated Customer", "updatedcustomer@example.com", "1234567890");
        customer.setId(1L);
        given(customerService.updateCustomer(org.mockito.ArgumentMatchers.any(Customer.class))).willReturn(customer);

        mockMvc.perform(put("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Customer\", \"email\": \"updatedcustomer@example.com\", \"phoneNumber\": \"1234567890\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Customer")));
    }

    @Test
    void updateCustomer_HandleException() throws Exception {
        given(customerService.updateCustomer(org.mockito.ArgumentMatchers.any(Customer.class))).willThrow(new RuntimeException("Error"));

        mockMvc.perform(put("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Faulty Customer\", \"email\": \"faulty@example.com\", \"phoneNumber\": \"1234567890\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCustomer_ShouldDeleteCustomer() throws Exception {
        doNothing().when(customerService).deleteCustomer(1L);

        mockMvc.perform(delete("/customers/1"))
                .andExpect(status().isOk());
    }
}