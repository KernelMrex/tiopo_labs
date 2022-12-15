package lab3.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoneyTest {
    @Test
    void moneyCanBeCompared() {
        assertEquals(
                new Money(10000, new Currency("RUB", 2)),
                new Money(10000, new Currency("RUB", 2))
        );

        assertNotEquals(
                new Money(10000, new Currency("RUB", 2)),
                new Money(20000, new Currency("RUB", 2))
        );

        assertNotEquals(
                new Money(10000, new Currency("USD", 2)),
                new Money(10000, new Currency("RUB", 2))
        );

        assertNotEquals(
                new Money(10000, new Currency("RUB", 2)),
                new Money(10000, new Currency("RUB", 3))
        );
    }

    @Test
    void moneyCanBeAddedWithSameCurrency() {
        var money1 = new Money(10000, new Currency("RUB", 2));
        var money2 = new Money(20000, new Currency("RUB", 2));

        assertDoesNotThrow(() -> assertEquals(money1.add(money2), new Money(30000, new Currency("RUB", 2))));
    }

    @Test
    void moneyCanNotBeAddedWithDifferentCurrency() {
        var money1 = new Money(10000, new Currency("RUB", 2));
        var money2 = new Money(20000, new Currency("USD", 3));

        assertThrows(CurrencyDoesNotMatchException.class, () -> money1.add(money2));
    }
}
