package com.restaurant.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.models.entities.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}