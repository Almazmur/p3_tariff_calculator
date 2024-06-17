package ru.fastdelivery.domain.common.dimension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DimensionsTest {
    public static final BigDecimal LENGTH_MILLIMETERS = BigDecimal.valueOf(345);
    public static final BigDecimal WIDTH_MILLIMETERS = BigDecimal.valueOf(589);
    public static final BigDecimal HEIGHT_MILLIMETERS = BigDecimal.valueOf(234);
    Dimensions dimensions;

    @BeforeEach
    void init() {
        dimensions = new Dimensions(
                LENGTH_MILLIMETERS,
                WIDTH_MILLIMETERS,
                HEIGHT_MILLIMETERS
        );

    }

    @Test
    @DisplayName("Попытка создать отрицательной размер каждой из сторон  -> исключение")
    void whenDimensionBelowZero_thenException() {
        var dimensionBelowZero = new BigDecimal("-1");

        assertThatThrownBy(() -> new Dimensions(dimensionBelowZero, WIDTH_MILLIMETERS, HEIGHT_MILLIMETERS))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Dimensions(LENGTH_MILLIMETERS, dimensionBelowZero, HEIGHT_MILLIMETERS))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Dimensions(LENGTH_MILLIMETERS, WIDTH_MILLIMETERS, dimensionBelowZero))
                .isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    void whenCallCalculateVolumeOfCubicMeters_thenReturnVolumeOfCubicMeters() {
        var actual = dimensions.calculateVolumeOfCubicMeters();
        var expected = BigDecimal.valueOf(0.0525);

        assertThat(actual).isEqualByComparingTo(expected);

    }

}
