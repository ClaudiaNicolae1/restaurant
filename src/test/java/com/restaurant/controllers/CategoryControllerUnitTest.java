package com.restaurant.controllers;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.restaurant.models.entities.Category;
import com.restaurant.models.entities.MenuItem;
import com.restaurant.services.CategoryService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerUnitTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = standaloneSetup(categoryController).build();
    }

    @Test
    void getAllCategories_ShouldReturnCategories() throws Exception {
        Category category1 = new Category("Category1", "Description1");
        Category category2 = new Category("Category2", "Description2");
        given(categoryService.getAllCategories()).willReturn(Arrays.asList(category1, category2));

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Category1")))
                .andExpect(jsonPath("$[1].name", is("Category2")));
    }

    @Test
    void getCategoryById_ShouldReturnCategory() throws Exception {
        Category category = new Category("Category1", "Description1");
        category.setId(1L);
        given(categoryService.getCategoryById(1L)).willReturn(Optional.of(category));

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Category1")));
    }

    @Test
    void getCategoryById_NotFound() throws Exception {
        given(categoryService.getCategoryById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createCategory_ShouldCreateCategory() throws Exception {
        Category category = new Category("New Category", "New Description");
        category.setId(1L);
        given(categoryService.createCategory(org.mockito.ArgumentMatchers.any(Category.class))).willReturn(category);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"New Category\", \"description\": \"New Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("New Category")));
    }

    @Test
    void updateCategory_ShouldUpdateCategory() throws Exception {
        Category category = new Category("Updated Category", "Updated Description");
        category.setId(1L);
        given(categoryService.updateCategory(org.mockito.ArgumentMatchers.any(Category.class))).willReturn(category);

        mockMvc.perform(put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"name\": \"Updated Category\", \"description\": \"Updated Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Updated Category")));
    }

    @Test
    void deleteCategory_ShouldDeleteCategory() throws Exception {
        doNothing().when(categoryService).deleteCategory(1L);

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isOk());
    }
}