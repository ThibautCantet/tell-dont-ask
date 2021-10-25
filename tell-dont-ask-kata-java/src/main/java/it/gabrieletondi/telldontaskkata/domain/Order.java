package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public Order() {
        status = OrderStatus.CREATED;
        items = new ArrayList<>();
        currency = "EUR";
        total = new BigDecimal("0.00");
        tax = new BigDecimal("0.00");
    }

    public BigDecimal getTotal() {
        return total;
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addItem(Product product, int quantity) {
        final OrderItem orderItem = new OrderItem(product, quantity);
        items.add(orderItem);
        total = total.add(orderItem.getTaxedAmount());
        tax = tax.add(orderItem.getTax());
    }
}
