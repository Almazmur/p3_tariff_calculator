package ru.fastdelivery.domain.common.dimension;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Общий класс габаритов упаковки
 *
 * @param lengthMillimeters длина в миллиметрах
 * @param widthMillimeters ширина в миллиметрах
 * @param heightMillimeters высота в миллиметрах
 */

public record Dimensions(BigDecimal lengthMillimeters,
                         BigDecimal widthMillimeters,
                         BigDecimal heightMillimeters
) {

    public Dimensions {
        if (isLessThanZero(lengthMillimeters) ||
                isLessThanZero(widthMillimeters) ||
                isLessThanZero(heightMillimeters)) {
            throw new IllegalArgumentException("Dimension cannot be below Zero!");
        }
    }

    public BigDecimal calculateVolumeOfCubicMeters() {

        return (roundNumber(lengthMillimeters)
                .multiply(roundNumber(widthMillimeters))
                .multiply(roundNumber(heightMillimeters)))
                .divide(new BigDecimal(1_000_000_000));

    }

    private BigDecimal roundNumber(BigDecimal number) {
        BigDecimal numberRoundingFactor = new BigDecimal("50");
        return number.divide(numberRoundingFactor, 0, RoundingMode.HALF_UP).multiply(numberRoundingFactor);
    }

    private static boolean isLessThanZero(BigDecimal dimension) {
        return BigDecimal.ZERO.compareTo(dimension) > 0;
    }


}