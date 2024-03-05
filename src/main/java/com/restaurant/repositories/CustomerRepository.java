package com.restaurant.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.models.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}