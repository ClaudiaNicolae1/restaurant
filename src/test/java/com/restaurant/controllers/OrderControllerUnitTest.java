package com.restaurant.controllers;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import java.text.SimpleDateFormat;
import com.restaurant.models.dtos.OrderDTO;
import com.restaurant.models.entities.Customer;
import com.restaurant.models.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.models.entities.Order;
import com.restaurant.services.OrderService;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderControllerUnitTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void getAllOrders_ShouldReturnOrders() throws Exception {
        OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setId(1L);
        orderDTO1.setOrderDate(new Date());
        orderDTO1.setTotalPrice(100.0);
        orderDTO1.setStatus("PENDING");
        orderDTO1.setCustomerName("Nobody");

        OrderDTO orderDTO2 = new OrderDTO();
        orderDTO2.setId(2L);
        orderDTO2.setOrderDate(new Date());
        orderDTO2.setTotalPrice(150.0);
        orderDTO2.setStatus("COMPLETED");
        orderDTO2.setCustomerName("Somebody");

        given(orderService.getAllOrders()).willReturn(Arrays.asList(orderDTO1, orderDTO2));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    void getOrderById_ShouldReturnOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        orderDTO.setOrderDate(new Date());
        orderDTO.setTotalPrice(100.0);
        orderDTO.setStatus("PENDING");
        orderDTO.setCustomerName("Nobody");

        given(orderService.getOrderById(1L)).willReturn(Optional.of(orderDTO));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("PENDING")));
    }

    @Test
    void getOrderById_NotFound() throws Exception {
        given(orderService.getOrderById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createOrder_ShouldCreateOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = dateFormat.parse("2022-09-15T10:00:00");
        order.setOrderDate(date);
        order.setStatus(Status.COMPLETED);
        given(orderService.createOrder(org.mockito.ArgumentMatchers.any(Order.class))).willReturn(order);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\": 1, \"orderDate\": \"2022-09-15T10:00:00\", \"status\": \"PENDING\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void createOrder_CustomerNotFound() throws Exception {
        given(orderService.createOrder(org.mockito.ArgumentMatchers.any(Order.class))).willThrow(new RuntimeException("Customer not found"));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\": 99, \"orderDate\": \"2022-09-15T10:00:00\", \"status\": \"PENDING\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Customer not found"));
    }

    @Test
    void updateOrder_ShouldUpdateOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setStatus(Status.COMPLETED);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = dateFormat.parse("2022-09-15T10:00:00");
        order.setOrderDate(date);
        given(orderService.updateOrder(org.mockito.ArgumentMatchers.any(Order.class))).willReturn(order);

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"customerId\": 1, \"orderDate\": \"2022-09-15T10:00:00\", \"status\": \"COMPLETED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void updateOrder_NotFound() throws Exception {
        given(orderService.updateOrder(org.mockito.ArgumentMatchers.any(Order.class))).willThrow(new RuntimeException("Order not found."));

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"customerId\": 1, \"orderDate\": \"2022-09-15T10:00:00\", \"status\": \"COMPLETED\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Order not found."));
    }

    @Test
    void updateOrder_HandleException() throws Exception {
        given(orderService.updateOrder(org.mockito.ArgumentMatchers.any(Order.class))).willThrow(new RuntimeException("Error"));

        mockMvc.perform(put("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"customerId\": 1, \"orderDate\": \"2022-09-15T10:00:00\", \"status\": \"PENDING\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteOrder_ShouldDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk());
    }
}
