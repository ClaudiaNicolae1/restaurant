package com.restaurant.models.entities;
import jakarta.persistence.*;
@Entity
@Table(name = "order_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Transient
    private Long _orderId;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private MenuItem menuItem;

    @Transient
    private Long menuItemId;

    private Integer quantity;

    public OrderDetail(){}

    public OrderDetail(Integer quantity) {
        this.quantity = quantity;
    }

    public Long get_orderId() {
        return _orderId;
    }

    public void set_orderId(Long _orderId) {
        this._orderId = _orderId;
    }

    public Long getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Long menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
