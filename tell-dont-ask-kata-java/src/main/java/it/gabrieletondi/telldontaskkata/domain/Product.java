package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

public class Product {
    private final String name;
    private final BigDecimal price;
    private final Category category;

    public Product(String name, BigDecimal price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getUnitaryTax() {
        return price.divide(valueOf(100))
                .multiply(category.getTaxPercentage()).setScale(2, HALF_UP);
    }

    public BigDecimal getUnitaryTaxedAmount() {
        return price.add(getUnitaryTax()).setScale(2, HALF_UP);
    }

    public BigDecimal getTaxedAmount(int quantity) {
        return getUnitaryTaxedAmount().multiply(BigDecimal.valueOf(quantity)).setScale(2, HALF_UP);
    }

    public OrderItem constructOrderItem(int quantity) {
        final OrderItem orderItem = new OrderItem();
        orderItem.setProduct(this);
        orderItem.setQuantity(quantity);
        orderItem.setTax(getUnitaryTax().multiply(BigDecimal.valueOf(quantity)));
        orderItem.setTaxedAmount(getTaxedAmount(quantity));
        return orderItem;
    }
}
