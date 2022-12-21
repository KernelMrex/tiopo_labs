package lab3.cart;

import lab3.money.Currency;
import lab3.money.CurrencyDoesNotMatchException;
import lab3.money.Money;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final ArrayList<OrderItem> items;
    private Money total;

    public Order(Currency currency) {
        this.items = new ArrayList<>();
        this.total = new Money(0, currency);
    }

    public void add(OrderItem item) throws OrderException {
        try {
            this.total = total.add(item.price());
            this.items.add(item);
        } catch (CurrencyDoesNotMatchException e) {
            throw new OrderException();
        }
    }

    public Money getTotal() {
        return total;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }
}
