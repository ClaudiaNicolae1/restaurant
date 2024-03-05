package com.restaurant.services;
import com.restaurant.models.dtos.OrderDTO;
import com.restaurant.models.dtos.OrderDetailDTO;
import com.restaurant.models.entities.Customer;
import com.restaurant.models.entities.MenuItem;
import com.restaurant.models.entities.Order;
import com.restaurant.models.entities.OrderDetail;
import com.restaurant.repositories.CustomerRepository;
import com.restaurant.repositories.MenuItemRepository;
import com.restaurant.repositories.OrderDetailRepository;
import com.restaurant.repositories.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderDetailRepository orderDetailRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    public OrderService(MenuItemRepository menuItemRepository,OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,CustomerRepository customerRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<OrderDTO> getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(order -> modelMapper.map(order, OrderDTO.class));
    }

    public Order createOrder(Order order) {
        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        if (!orderRepository.existsById(order.getId())) {
            throw new RuntimeException("Order not found.");
        }
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        orderDetailRepository.deleteById(orderId);
    }

    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        Order order = orderRepository.findById(orderDetail.get_orderId()).orElseThrow(() -> new RuntimeException("Order not found"));
        MenuItem menuItem = menuItemRepository.findById(orderDetail.getMenuItemId()).orElseThrow(() -> new RuntimeException("MenuItem not found"));
        orderDetail.setOrder(order);
        orderDetail.setMenuItem(menuItem);
        return orderDetailRepository.save(orderDetail);
    }

    public Optional<OrderDetailDTO> getOrderDetailById(Long orderDetailId) {
        return orderDetailRepository.findById(orderDetailId).map(orderD -> modelMapper.map(orderD, OrderDetailDTO.class));
    }

    public OrderDetail updateOrderDetail(OrderDetail orderDetail) {
        if (!orderDetailRepository.existsById(orderDetail.getId())) {
            throw new RuntimeException("Order detail not found.");
        }
        return orderDetailRepository.save(orderDetail);
    }

    public void deleteOrderDetail(Long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }

    public List<OrderDetailDTO> getAllOrderDetailsForOrder(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        return orderDetails.stream()
                .map(orderD -> modelMapper.map(orderD, OrderDetailDTO.class))
                .collect(Collectors.toList());
    }
}

