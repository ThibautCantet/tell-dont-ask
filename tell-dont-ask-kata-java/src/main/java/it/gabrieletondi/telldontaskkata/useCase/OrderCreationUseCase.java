package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;

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
            order.addItem(product, itemRequest.getQuantity());
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
