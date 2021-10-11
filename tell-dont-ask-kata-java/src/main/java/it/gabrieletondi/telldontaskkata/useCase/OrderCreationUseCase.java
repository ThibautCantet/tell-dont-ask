package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.math.BigDecimal.valueOf;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;
    private final ProductCatalog productCatalog;

    public OrderCreationUseCase(OrderRepository orderRepository, ProductCatalog productCatalog) {
        this.orderRepository = orderRepository;
        this.productCatalog = productCatalog;
    }

    public void run(SellItemsRequest request) {
        Order order = new Order();

        for (SellItemRequest itemRequest : request.getRequests()) {
            Product product = getProduct(itemRequest);
            final OrderItem orderItem = new OrderItem(product, itemRequest.getQuantity());

            order.addItem(orderItem);
        }

        orderRepository.save(order);
    }

    private Product getProduct(SellItemRequest itemRequest) {
        Product product = productCatalog.getByName(itemRequest.getProductName());

        if (product == null) {
            throw new UnknownProductException();
        }
        return product;
    }
}
