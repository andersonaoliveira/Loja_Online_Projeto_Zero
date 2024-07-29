package com.zero.checkoutservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private List<OrderItem> items;
    private double totalAmount;
    private String shippingAddress;
    private String billingAddress;
    private String status; // e.g., "PENDING", "COMPLETED"
}
