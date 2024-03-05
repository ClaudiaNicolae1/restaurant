package com.restaurant.services;

import com.restaurant.models.dtos.MenuItemDTO;
import com.restaurant.models.dtos.OrderDTO;
import com.restaurant.models.dtos.OrderDetailDTO;
import com.restaurant.models.entities.MenuItem;
import com.restaurant.models.entities.OrderDetail;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.restaurant.models.entities.Order;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Order, OrderDTO>() {
            @Override
            protected void configure() {
                map().setCustomerName(source.getCustomer().getName());
                map().setTotalPrice(source.getTotalPrice());
            }
        });

        modelMapper.addMappings(new PropertyMap<MenuItem, MenuItemDTO>() {
            @Override
            protected void configure() {
                map().setCategory(source.getCategory().getName());
            }
        });


        modelMapper.addMappings(new PropertyMap<OrderDetail, OrderDetailDTO>() {
            @Override
            protected void configure() {
                map().setDate(source.getOrder().getOrderDate());
                map().setPrice(source.getMenuItem().getPrice());
                map().setCategory(source.getMenuItem().getCategory().getName());
            }
        });
        return modelMapper;
    }
}