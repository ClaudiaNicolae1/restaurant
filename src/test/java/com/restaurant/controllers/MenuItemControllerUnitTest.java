package com.restaurant.controllers;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import com.restaurant.models.dtos.MenuItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.restaurant.models.entities.MenuItem;
import com.restaurant.services.MenuItemService;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MenuItemControllerUnitTest {

    @Mock
    private MenuItemService menuItemService;

    @InjectMocks
    private MenuItemController menuItemController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(menuItemController).build();
    }

    @Test
    void getAllMenuItems_ShouldReturnMenuItems() throws Exception {
        MenuItemDTO menuItemDto1 = new MenuItemDTO();
        menuItemDto1.setName("Item 1");
        menuItemDto1.setPrice(10.0);

        MenuItemDTO menuItemDto2 = new MenuItemDTO();
        menuItemDto2.setName("Item 2");
        menuItemDto2.setPrice(15.0);

        given(menuItemService.getAllMenuItems()).willReturn(Arrays.asList(menuItemDto1, menuItemDto2));

        mockMvc.perform(get("/menuItems"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Item 1")))
                .andExpect(jsonPath("$[1].name", is("Item 2")));
    }

    @Test
    void getMenuItemById_ShouldReturnMenuItem() throws Exception {
        MenuItemDTO menuItemDto = new MenuItemDTO();
        menuItemDto.setId(1L);
        menuItemDto.setName("Item 1");
        menuItemDto.setPrice(10.0);

        given(menuItemService.getMenuItemById(1L)).willReturn(Optional.of(menuItemDto));

        mockMvc.perform(get("/menuItems/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Item 1")));
    }

    @Test
    void getMenuItemById_NotFound() throws Exception {
        given(menuItemService.getMenuItemById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/menuItems/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createMenuItem_ShouldCreateMenuItem() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("New Item");
        menuItem.setPrice(20.0);

        given(menuItemService.createMenuItem(org.mockito.ArgumentMatchers.any(MenuItem.class))).willReturn(menuItem);

        mockMvc.perform(post("/menuItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Item\", \"price\": 20.0, \"categoryId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Item")));
    }

    @Test
    void updateMenuItem_ShouldUpdateMenuItem() throws Exception {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(1L);
        menuItem.setName("Updated Item");
        menuItem.setPrice(25.0);

        given(menuItemService.updateMenuItem(org.mockito.ArgumentMatchers.any(MenuItem.class))).willReturn(menuItem);

        mockMvc.perform(put("/menuItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Item\", \"price\": 25.0, \"categoryId\": 1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Item")));
    }

    @Test
    void updateMenuItem_HandleException() throws Exception {
        given(menuItemService.updateMenuItem(org.mockito.ArgumentMatchers.any(MenuItem.class))).willThrow(new RuntimeException("Error"));

        mockMvc.perform(put("/menuItems")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Faulty Item\", \"price\": 20.0, \"categoryId\": 1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteMenuItem_ShouldDeleteMenuItem() throws Exception {
        doNothing().when(menuItemService).deleteMenuItem(1L);

        mockMvc.perform(delete("/menuItems/1"))
                .andExpect(status().isOk());
    }
}