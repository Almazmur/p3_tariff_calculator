package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Dimensions;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TariffCalculatorForBasicCostTest {
    Currency currency;

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final DimensionsPriseProvider dimensionsPriseProvider = mock(DimensionsPriseProvider.class);
    final TariffCalculatorByWeight tariffCalculatorByWeight = new TariffCalculatorByWeight(weightPriceProvider);
    final TariffCalculatorByDimensions tariffCalculatorByDimensions = new TariffCalculatorByDimensions(dimensionsPriseProvider);
    final TariffCalculatorForBasicCost  tariffCalculatorForBasicCost = new TariffCalculatorForBasicCost();
    Dimensions dimensions;
    Weight weight;

    @BeforeEach
    void init() {
        currency = new CurrencyFactory(code -> true).create("RUB");
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var pricePerCubicMeter = new Price(BigDecimal.valueOf(1000), currency);

        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);

        when(dimensionsPriseProvider.minimalPrice()).thenReturn(minimalPrice);
        when(dimensionsPriseProvider.costPerCubicMeter()).thenReturn(pricePerCubicMeter);

    }


    @Test
    @DisplayName("Если цена по весу больше -> успешный возврат цены по весу")
    void whenPriceByWeighMore_thenReturnPriceByWeigh() {
        dimensions = new Dimensions(BigDecimal.valueOf(300), BigDecimal.valueOf(500), BigDecimal.valueOf(200));
        weight = new Weight(BigInteger.valueOf(1200));

        var shipment = new Shipment(List.of(new Pack(weight, dimensions)), currency);

        var actualPrice = tariffCalculatorForBasicCost.calc(
                tariffCalculatorByWeight,
                tariffCalculatorByDimensions,
                shipment
        );

        var expectedPrice = new Price(BigDecimal.valueOf(120), currency);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);

    }

    @Test
    @DisplayName("Если цена по габаритам больше -> успешный возврат цены по габаритам")
    void whenPriceByDimensionsMore_thenReturnPriceByDimensions() {
        dimensions = new Dimensions(BigDecimal.valueOf(300), BigDecimal.valueOf(500), BigDecimal.valueOf(200));
        weight = new Weight(BigInteger.valueOf(100));

        var shipment = new Shipment(List.of(new Pack(weight, dimensions)), currency);

        var actualPrice = tariffCalculatorForBasicCost.calc(
                tariffCalculatorByWeight,
                tariffCalculatorByDimensions,
                shipment
        );

        var expectedPrice = new Price(BigDecimal.valueOf(30), currency);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);

    }
}
