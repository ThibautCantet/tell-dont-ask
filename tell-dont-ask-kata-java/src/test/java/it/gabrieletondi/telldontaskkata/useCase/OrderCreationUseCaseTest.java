package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Category;
import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderItem;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.doubles.InMemoryProductCatalog;
import it.gabrieletondi.telldontaskkata.doubles.TestOrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrderCreationUseCaseTest {
    public static final String SALAD = "salad";
    public static final String TOMATO = "tomato";
    private final TestOrderRepository orderRepository = new TestOrderRepository();
    private static final Category food = new Category("food", new BigDecimal("10"));
    private final ProductCatalog productCatalog = new InMemoryProductCatalog(
            asList(
                    new Product(
                            SALAD,
                            new BigDecimal("3.56"),
                            food)
                    ,
                    new Product(
                            TOMATO,
                            new BigDecimal("4.65"),
                            food)
            )
    );
    private final OrderCreationUseCase useCase = new OrderCreationUseCase(orderRepository, productCatalog);

    @Test
    public void sellMultipleItems() throws Exception {
        // given
        SellItemRequest saladRequest = new SellItemRequest(2, SALAD);

        SellItemRequest tomatoRequest = new SellItemRequest(3, TOMATO);

        final SellItemsRequest request = new SellItemsRequest(asList(saladRequest, tomatoRequest));

        OrderItem firstOrderItem = new OrderItem(new Product(SALAD, new BigDecimal("3.56"), food), 2);
        OrderItem secondOrderItem = new OrderItem(new Product(TOMATO, new BigDecimal("4.65"), food), 3);

        // when
        useCase.run(request);

        // then
        final Order insertedOrder = orderRepository.getSavedOrder();
        assertThat(insertedOrder.getStatus(), is(OrderStatus.CREATED));
        assertThat(insertedOrder.getTotal(), is(new BigDecimal("23.20")));
        assertThat(insertedOrder.getTax(), is(new BigDecimal("2.13")));
        assertThat(insertedOrder.getCurrency(), is("EUR"));
        assertThat(insertedOrder.getItems(), hasSize(2));
        assertThat(insertedOrder.getItems().get(0).getTaxedAmount(), is(new BigDecimal("7.84")));
        assertThat(insertedOrder.getItems().get(0).getTax(), is(new BigDecimal("0.72")));
        assertThat(insertedOrder.getItems().get(1).getTaxedAmount(), is(new BigDecimal("15.36")));
        assertThat(insertedOrder.getItems().get(1).getTax(), is(new BigDecimal("1.41")));
        assertThat(insertedOrder.getItems(), is(asList(firstOrderItem, secondOrderItem)));
    }

    @Test(expected = UnknownProductException.class)
    public void unknownProduct() throws Exception {
        SellItemRequest unknownProductRequest = new SellItemRequest(0, "unknown product");
        SellItemsRequest request = new SellItemsRequest(unknownProductRequest);

        useCase.run(request);
    }
}
