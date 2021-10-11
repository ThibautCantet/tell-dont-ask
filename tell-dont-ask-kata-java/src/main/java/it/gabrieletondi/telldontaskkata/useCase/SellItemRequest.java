package it.gabrieletondi.telldontaskkata.useCase;

public class SellItemRequest {
    private final int quantity;
    private final String productName;

    public SellItemRequest(int quantity, String productName) {
        this.quantity = quantity;
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }
}
