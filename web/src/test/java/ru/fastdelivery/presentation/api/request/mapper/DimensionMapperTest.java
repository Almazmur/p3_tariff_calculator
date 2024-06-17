package ru.fastdelivery.presentation.api.request.mapper;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.dimension.Dimensions;
import ru.fastdelivery.presentation.api.mapper.DimensionMapper;
import ru.fastdelivery.presentation.api.request.CargoPackage;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class DimensionMapperTest {
    @Test
    @DisplayName("При передаче грузовой упаковки -> получаем габариты")
    void whenTransferredCargoPackage_thanReturnDimensions() {
        CargoPackage cargoPackage = new CargoPackage(new BigInteger("1200"),
                new BigDecimal("300"),
                new BigDecimal("500"),
                new BigDecimal("200"));
        var actual = DimensionMapper.cargoPackageToDimension(cargoPackage);
        var expected = new Dimensions(new BigDecimal("300"),
                new BigDecimal("500"),
                new BigDecimal("200"));

        assertThat(actual).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expected);
    }
}
