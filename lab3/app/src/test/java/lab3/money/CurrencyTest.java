package lab3.money;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CurrencyTest {
    @Test
    void currencyMayBeEqual() {
        var currency1 = new Currency("RUB", 2);
        var currency2 = new Currency("RUB", 2);
        assertEquals(currency1, currency1);
        assertEquals(currency1, currency2);
    }

    @Test
    void currenciesMayBeDifferent() {
        var currency1 = new Currency("RUB", 2);
        var currency2 = new Currency("USD", 2);
        assertNotEquals(currency1, currency2);

        var currency3 = new Currency("USD", 2);
        var currency4 = new Currency("USD", 3);
        assertNotEquals(currency3, currency4);
    }

    @Test
    void mayBeComparedWithNotACurrencyObject() {
        //noinspection AssertBetweenInconvertibleTypes
        assertNotEquals(new Currency("USD", 2), "Not a currency object");
    }
}
