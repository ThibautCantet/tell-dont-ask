package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;

public class OrderItem {
    private final Product product;
    private final int quantity;
    private final BigDecimal taxedAmount;
    private final BigDecimal tax;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;

        this.tax = product.getUnitaryTax().multiply(BigDecimal.valueOf(quantity));
        this.taxedAmount = product.getTaxedAmount(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTaxedAmount() {
        return taxedAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }
}
