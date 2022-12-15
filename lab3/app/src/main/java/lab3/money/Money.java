package lab3.money;

import java.util.ArrayList;
import java.util.List;

public class Money {
    private final int amount;
    private final Currency currency;

    public Money(int amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Money add(Money money) throws CurrencyDoesNotMatchException {
        if (!currency.equals(money.currency)) {
            throw new CurrencyDoesNotMatchException();
        }
        return new Money(amount + money.amount, currency);
    }

    public Money sub(Money money) throws CurrencyDoesNotMatchException {
        if (!currency.equals(money.currency)) {
            throw new CurrencyDoesNotMatchException();
        }
        return new Money(amount - money.amount, currency);
    }

    public List<Money> split(int partsAmount) {
        int signCoefficient = this.amount < 0 ? -1 : 1;
        var amount = this.amount * signCoefficient;

        return new ArrayList<>() {{
            int fullPart = amount / partsAmount;

            int withAddition = amount % partsAmount;
            for (int i = 0; i < withAddition; i++)
            {
                add(new Money((fullPart + 1) * signCoefficient, currency));
            }

            int withOutAddition = amount - withAddition;
            for (int i = 0; i < withOutAddition; i++)
            {
                add(new Money(fullPart * signCoefficient, currency));
            }
        }};
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Money otherMoney)) {
            return false;
        }

        return currency.equals(otherMoney.currency)
                && amount == otherMoney.amount;
    }
}
