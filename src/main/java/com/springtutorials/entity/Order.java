package com.springtutorials.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderid;

    @ManyToOne
    @JoinColumn(name = "user_id") 
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();


    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private Order_Status status;

    private double totalAmount;
    
    @ManyToOne
    @JoinColumn(name = "address_id")
    private ShippingAddress shippingAddress;

    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;
    
    /*public void calculatingTotalAmount() {
        double total_amount = 0;
        for (OrderItem item : items) {
            if (item.getProduct() != null) {
                double price = item.getProduct().getPrice();
                int quantity = item.getQuantity();
                total_amount += price * quantity;
            }
        }
        this.totalAmount = total_amount;
    }*/

    
    
    public Long getOrderid() {
        return orderid;
    }

    public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Order_Status getStatus() {
        return status;
    }

    public void setStatus(Order_Status status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public ShippingAddress getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(ShippingAddress shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
}
