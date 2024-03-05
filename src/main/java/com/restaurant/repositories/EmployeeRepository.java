package com.restaurant.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.models.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}