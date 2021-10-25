package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import it.gabrieletondi.telldontaskkata.useCase.ApprovedOrderCannotBeRejectedException;
import it.gabrieletondi.telldontaskkata.useCase.OrderCannotBeShippedException;
import it.gabrieletondi.telldontaskkata.useCase.OrderCannotBeShippedTwiceException;
import it.gabrieletondi.telldontaskkata.useCase.RejectedOrderCannotBeApprovedException;
import it.gabrieletondi.telldontaskkata.useCase.ShippedOrdersCannotBeChangedException;

import static it.gabrieletondi.telldontaskkata.domain.OrderStatus.*;

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

    public Order(OrderStatus status, int orderId) {
        this.id = orderId;
        this.status = status;
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

    public int getId() {
        return id;
    }

    public void addItem(Product product, int quantity) {
        final OrderItem orderItem = new OrderItem(product, quantity);
        items.add(orderItem);
        total = total.add(orderItem.getTaxedAmount());
        tax = tax.add(orderItem.getTax());
    }

    public void approve(boolean approved) {
        if (this.status.equals(OrderStatus.SHIPPED)) {
            throw new ShippedOrdersCannotBeChangedException();
        }

        if (approved && this.status.equals(OrderStatus.REJECTED)) {
            throw new RejectedOrderCannotBeApprovedException();
        }

        if (!approved && this.status.equals(OrderStatus.APPROVED)) {
            throw new ApprovedOrderCannotBeRejectedException();
        }

        status = approved ? OrderStatus.APPROVED : OrderStatus.REJECTED;
    }

    public void verifyShippingStatus() {
        if (this.status.equals(CREATED) || this.status.equals(REJECTED)) {
            throw new OrderCannotBeShippedException();
        }

        if (this.status.equals(SHIPPED)) {
            throw new OrderCannotBeShippedTwiceException();
        }
    }

    public void ship() {
        status = OrderStatus.SHIPPED;
    }
}
