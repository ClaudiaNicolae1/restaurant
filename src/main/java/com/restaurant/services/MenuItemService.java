package com.restaurant.services;

import com.restaurant.models.dtos.MenuItemDTO;
import com.restaurant.models.dtos.OrderDTO;
import com.restaurant.models.entities.Category;
import com.restaurant.models.entities.Customer;
import com.restaurant.models.entities.MenuItem;
import com.restaurant.repositories.CategoryRepository;
import com.restaurant.repositories.MenuItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public MenuItemService(MenuItemRepository menuItemRepository, CategoryRepository categoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<MenuItemDTO> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        return menuItems.stream()
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<MenuItemDTO> getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .map(menuItem -> modelMapper.map(menuItem, MenuItemDTO.class));
    }

    public MenuItem createMenuItem(MenuItem menuItem) {
        Category category = categoryRepository.findById(menuItem.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        menuItem.setCategory(category);
        return menuItemRepository.save(menuItem);
    }

    public MenuItem updateMenuItem(MenuItem menuItem) {
        if (!menuItemRepository.existsById(menuItem.getId())) {
            throw new RuntimeException("Menu item not found.");
        }
        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }
}
