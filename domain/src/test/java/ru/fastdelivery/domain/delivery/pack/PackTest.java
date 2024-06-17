package ru.fastdelivery.domain.delivery.pack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimension.Dimensions;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PackTest {
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
    void whenWeightMoreThanMaxWeight_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(150_001));
        assertThatThrownBy(() -> new Pack(weight, dimensions))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenDimensionMoreThanMaxDimension_thenThrowException() {
        var weight = new Weight(BigInteger.valueOf(4564));
        var dimensionMoreThanMaxValue = BigDecimal.valueOf(1501);
        var dimensionWithLengthMoreMax = new Dimensions(dimensionMoreThanMaxValue, WIDTH_MILLIMETERS, HEIGHT_MILLIMETERS);
        var dimensionWithWidthMoreMax = new Dimensions(LENGTH_MILLIMETERS, dimensionMoreThanMaxValue, HEIGHT_MILLIMETERS);
        var dimensionWithHeightMoreMax = new Dimensions(LENGTH_MILLIMETERS, WIDTH_MILLIMETERS, dimensionMoreThanMaxValue);

        assertThatThrownBy(() -> new Pack(weight, dimensionWithLengthMoreMax))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Pack(weight, dimensionWithWidthMoreMax))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> new Pack(weight, dimensionWithHeightMoreMax))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenWeightAndDimensionsLessThanMaxValue_thenObjectCreated() {
        var actual = new Pack(new Weight(BigInteger.valueOf(1_000)), dimensions);
        assertThat(actual.weight()).isEqualTo(new Weight(BigInteger.valueOf(1_000)));
    }
}