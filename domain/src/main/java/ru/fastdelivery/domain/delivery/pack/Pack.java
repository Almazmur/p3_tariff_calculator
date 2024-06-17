package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.dimension.Dimensions;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 * @param dimensions габариты упаковки
 */
public record Pack(Weight weight, Dimensions dimensions) {

    private static final Weight MAX_WEIGHT = new Weight(BigInteger.valueOf(150_000));
    private static final BigDecimal MAX_DIMENSION = new BigDecimal(1500);

    public Pack {
        if (weight.greaterThan(MAX_WEIGHT)) {
            throw new IllegalArgumentException("Package can't be more than " + MAX_WEIGHT);
        }
        if (dimensions.lengthMillimeters().compareTo(MAX_DIMENSION) > 0 ||
                dimensions.widthMillimeters().compareTo(MAX_DIMENSION) > 0 ||
                dimensions.heightMillimeters().compareTo(MAX_DIMENSION) > 0) {
            throw new IllegalArgumentException("Dimension can't be more than " + MAX_DIMENSION);
        }
    }
}
