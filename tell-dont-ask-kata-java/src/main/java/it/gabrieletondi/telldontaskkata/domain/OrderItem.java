package it.gabrieletondi.telldontaskkata.domain;

import java.math.BigDecimal;
import java.util.Objects;

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

    public BigDecimal getTaxedAmount() {
        return taxedAmount;
    }

    public BigDecimal getTax() {
        return tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return quantity == orderItem.quantity && Objects.equals(product, orderItem.product) && Objects.equals(taxedAmount, orderItem.taxedAmount) && Objects.equals(tax, orderItem.tax);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity, taxedAmount, tax);
    }
}
