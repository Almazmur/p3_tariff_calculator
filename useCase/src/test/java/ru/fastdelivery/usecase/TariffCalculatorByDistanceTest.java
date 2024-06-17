package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coordinates.Departure;
import ru.fastdelivery.domain.common.coordinates.Destination;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TariffCalculatorByDistanceTest {
    final CoordinatesProvider coordinatesProvider = mock(CoordinatesProvider.class);
    final TariffCalculatorByDistance tariffCalculatorByDistance = new TariffCalculatorByDistance(coordinatesProvider);
    final Departure departure = new Departure(59.938659, 30.314457);
    final Destination destination = new Destination(57.626562, 39.893809);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");
    final Price basePrice = new Price(BigDecimal.TEN, currency);

    @BeforeEach
    void init() {
        when(coordinatesProvider.getMinLatitude()).thenReturn(45.0);
        when(coordinatesProvider.getMaxLatitude()).thenReturn(65.0);
        when(coordinatesProvider.getMinLongitude()).thenReturn(30.0);
        when(coordinatesProvider.getMaxLongitude()).thenReturn(96.0);
    }

    @Test
    @DisplayName("Расчет стоимости доставки с учетом расстояния -> успешно")
    void whenCalculatePriceByDistance_thenSuccess() {
        var actualPrice = tariffCalculatorByDistance.calc(departure, destination, basePrice, currency);
        var expectedPrice = new Price(BigDecimal.valueOf(13.52), currency);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }

    @Test
    @DisplayName("Попытка задать координаты больше или меньше допустимых -> исключение ")
    void whenCoordinatesOutOfRange_thenException() {
        double invalidMinValue =  29.0;
        double invalidMaxValue =  97.0;
        Departure invalidDeparture1 = new Departure(invalidMinValue, 30.314457);
        Departure invalidDeparture2 = new Departure(invalidMaxValue, 30.314457);
        Destination invalidDestination1 = new Destination(57.626562, invalidMinValue);
        Destination invalidDestination2 = new Destination(57.626562, invalidMaxValue);

        assertThatThrownBy(() -> tariffCalculatorByDistance.calc(invalidDeparture1, destination, basePrice, currency))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> tariffCalculatorByDistance.calc(invalidDeparture2, destination, basePrice, currency))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> tariffCalculatorByDistance.calc(departure, invalidDestination1, basePrice, currency))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> tariffCalculatorByDistance.calc(departure, invalidDestination2, basePrice, currency))
                .isInstanceOf(IllegalArgumentException.class);

    }

}
