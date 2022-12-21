package lab3.cart;

import lab3.money.Currency;
import lab3.money.Money;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    private static final Currency RUB = new Currency("RUB", 2);
    private static final Currency USD = new Currency("USD", 2);

    @Test
    public void orderItemCanBeAddedToOrder() {
        var order = new Order(RUB);

        var item1 = new OrderItem("Item 1", new Money(100, RUB));
        var item2 = new OrderItem("Item 2", new Money(200, RUB));

        assertDoesNotThrow(() -> order.add(item1));
        assertEquals(order.getTotal(), new Money(100, RUB));
        assertEquals(order.getItems(), new ArrayList<>(){{ add(item1); }});

        assertDoesNotThrow(() -> order.add(item2));
        assertEquals(order.getTotal(), new Money(300, RUB));
        assertEquals(order.getItems(), new ArrayList<>(){{ add(item1); add(item2); }});
    }

    @Test
    public void orderItemCanNotBeAddedToOrderWithDifferentCurrency() {
        var order = new Order(RUB);
        var item = new OrderItem("Item with USD price", new Money(100, USD));
        assertThrows(OrderException.class, () -> order.add(item));
    }
}
