package com.restaurant.controllers;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.restaurant.models.dtos.OrderDetailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.models.entities.OrderDetail;
import com.restaurant.services.OrderService;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class OrderDetailControllerUnitTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderDetailController orderDetailController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderDetailController).build();
    }

    @Test
    void getOrderDetailsByOrderId_ShouldReturnOrderDetails() throws Exception {
        OrderDetailDTO orderDetailDTO1 = new OrderDetailDTO();
        OrderDetailDTO orderDetailDTO2 = new OrderDetailDTO();

        given(orderService.getAllOrderDetailsForOrder(1L)).willReturn(Arrays.asList(orderDetailDTO1, orderDetailDTO2));

        mockMvc.perform(get("/orderDetails/{orderId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getOrderDetailById_ShouldReturnOrderDetail() throws Exception {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(1L);
        given(orderService.getOrderDetailById(1L)).willReturn(Optional.of(orderDetailDTO));

        mockMvc.perform(get("/orderDetails/detail/{orderDetailId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void createOrderDetail_ShouldCreateOrderDetail() throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        given(orderService.addOrderDetail(org.mockito.ArgumentMatchers.any(OrderDetail.class))).willReturn(orderDetail);

        String jsonContent = "{\"quantity\": 2, \"menuItemId\": 1, \"_orderId\": 1}";
        mockMvc.perform(post("/orderDetails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void updateOrderDetail_ShouldUpdateOrderDetail() throws Exception {
        OrderDetail orderDetail = new OrderDetail();
        given(orderService.updateOrderDetail(org.mockito.ArgumentMatchers.any(OrderDetail.class))).willReturn(orderDetail);

        String jsonContent = "{\"id\": 1, \"quantity\": 3, \"menuItemId\": 1, \"_orderId\": 1}";
        mockMvc.perform(put("/orderDetails")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    void deleteOrderDetail_ShouldDeleteOrderDetail() throws Exception {
        doNothing().when(orderService).deleteOrderDetail(1L);

        mockMvc.perform(delete("/orderDetails/{orderDetailId}", 1L))
                .andExpect(status().isOk());
    }
}