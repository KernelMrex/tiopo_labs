package lab3.money;

public class Currency {
    private final String code;
    private final int decimalPlaces;

    public Currency(String code, int decimalPlaces) {
        this.code = code;
        this.decimalPlaces = decimalPlaces;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Currency otherCurrency)) {
            return false;
        }

        return code.equalsIgnoreCase(otherCurrency.code)
                && decimalPlaces == otherCurrency.decimalPlaces;
    }
}
