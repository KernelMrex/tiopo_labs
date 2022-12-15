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

    @Test
    void moneyCanBeSubtractedWithSameCurrency() {
        var money1 = new Money(30000, new Currency("RUB", 2));
        var money2 = new Money(10000, new Currency("RUB", 2));

        assertDoesNotThrow(() -> assertEquals(
                money1.sub(money2),
                new Money(20000, new Currency("RUB", 2))
        ));
    }

    @Test
    void moneyCanNotBeSubtractedWithDifferentCurrency() {
        var money1 = new Money(30000, new Currency("RUB", 2));
        var money2 = new Money(10000, new Currency("USD", 2));

        assertThrows(CurrencyDoesNotMatchException.class, () -> money1.sub(money2));
    }

    @Test
    void moneyCanBeSplittedIntoManyEqualParts() {
        var money = new Money(400, new Currency("RUB", 2));

        var splittedMoney = money.split(6);

        assertEquals(
                splittedMoney.get(0),
                new Money(67, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(1),
                new Money(67, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(2),
                new Money(67, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(3),
                new Money(67, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(4),
                new Money(66, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(5),
                new Money(66, new Currency("RUB", 2))
        );
    }

    @Test
    void negativeMoneyCanBeSplittedIntoManyEqualParts() {
        var money = new Money(-400, new Currency("RUB", 2));

        var splittedMoney = money.split(6);

        assertEquals(
                splittedMoney.get(0),
                new Money(-67, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(1),
                new Money(-67, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(2),
                new Money(-67, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(3),
                new Money(-67, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(4),
                new Money(-66, new Currency("RUB", 2))
        );
        assertEquals(
                splittedMoney.get(5),
                new Money(-66, new Currency("RUB", 2))
        );
    }
}
