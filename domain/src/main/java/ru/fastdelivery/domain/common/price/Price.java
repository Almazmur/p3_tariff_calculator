package ru.fastdelivery.domain.common.price;

import ru.fastdelivery.domain.common.currency.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @param amount   значение цены
 * @param currency валюта цены
 */
public record Price(
        BigDecimal amount,
        Currency currency) implements Comparable<Price> {
    public Price {
        if (isLessThanZero(amount)) {
            throw new IllegalArgumentException("Price Amount cannot be below Zero!");
        }
    }

    private static boolean isLessThanZero(BigDecimal price) {
        return BigDecimal.ZERO.compareTo(price) > 0;
    }

    public Price multiply(BigDecimal amount) {
        return new Price(this.amount.multiply(amount).setScale(2, RoundingMode.CEILING), this.currency);
    }

    public Price max(Price price) {
        if (!currency.equals(price.currency)) {
            throw new IllegalArgumentException("Cannot compare Prices in difference Currency!");
        }
        return new Price(this.amount.max(price.amount), this.currency);
    }

    @Override
    public int compareTo(Price o) {
        return this.amount.compareTo(o.amount);
    }
}