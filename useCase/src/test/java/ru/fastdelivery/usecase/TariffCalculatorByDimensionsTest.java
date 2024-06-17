package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
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

public class TariffCalculatorByDimensionsTest {
    final DimensionsPriseProvider dimensionsPriseProvider = mock(DimensionsPriseProvider.class);

    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    final TariffCalculatorByDimensions tariffCalculatorByDimensions = new TariffCalculatorByDimensions(dimensionsPriseProvider);

    final Dimensions dimensions = new Dimensions(BigDecimal.valueOf(300), BigDecimal.valueOf(500), BigDecimal.valueOf(200));

    @Test
    @DisplayName("Расчет стоимости доставки по габаритам -> успешно")
    void whenCalculatePriceByDimensions_thenSuccess() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var pricePerCubicMeter = new Price(BigDecimal.valueOf(1000), currency);
        when(dimensionsPriseProvider.minimalPrice()).thenReturn(minimalPrice);
        when(dimensionsPriseProvider.costPerCubicMeter()).thenReturn(pricePerCubicMeter);

        var shipment = new Shipment(List.of(new Pack(new Weight(BigInteger.valueOf(1200)), dimensions)),
                new CurrencyFactory(code -> true).create("RUB"));

        var expectedPrice = new Price(BigDecimal.valueOf(30), currency);
        var actualPrice = tariffCalculatorByDimensions.calc(shipment);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);


    }
}
