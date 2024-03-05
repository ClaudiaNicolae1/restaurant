package com.restaurant.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.models.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
