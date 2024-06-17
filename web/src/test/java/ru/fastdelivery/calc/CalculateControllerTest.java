package ru.fastdelivery.calc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.fastdelivery.ControllerTest;
import ru.fastdelivery.domain.common.coordinates.Departure;
import ru.fastdelivery.domain.common.coordinates.Destination;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculatorByDimensions;
import ru.fastdelivery.usecase.TariffCalculatorByDistance;
import ru.fastdelivery.usecase.TariffCalculatorByWeight;
import ru.fastdelivery.usecase.TariffCalculatorForBasicCost;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CalculateControllerTest extends ControllerTest {

    final String baseCalculateApi = "/api/v1/calculate/";
    @MockBean
    TariffCalculatorByWeight tariffCalculatorByWeight;
    @MockBean
    TariffCalculatorByDimensions tariffCalculatorByDimensions;
    @MockBean
    TariffCalculatorForBasicCost tariffCalculatorForBasicCost;
    @MockBean
    TariffCalculatorByDistance tariffCalculatorByDistance;
    @MockBean
    CurrencyFactory currencyFactory;

    @BeforeEach
    void init() {
        var rub = new CurrencyFactory(code -> true).create("RUB");

        when(tariffCalculatorByWeight.calc(any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(tariffCalculatorByWeight.minimalPrice()).thenReturn(new Price(BigDecimal.valueOf(5), rub));
        when(tariffCalculatorByDimensions.calc(any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(tariffCalculatorForBasicCost.calc(any(), any(), any())).thenReturn(new Price(BigDecimal.valueOf(10), rub));
        when(tariffCalculatorByDistance.calc(any(), any(), any(), any())).thenReturn(new Price(BigDecimal.valueOf(20), rub));
    }

    @Test
    @DisplayName("Валидные данные для расчета стоимость -> Ответ 200")
    void whenValidInputData_thenReturn200() {
        var request = new CalculatePackagesRequest(
                List.of(new CargoPackage(BigInteger.TEN, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN)), "RUB",
                new Destination(57.626562, 39.893809), new Departure(59.938659, 30.314457));

        ResponseEntity<CalculatePackagesResponse> response =
                restTemplate.postForEntity(baseCalculateApi, request, CalculatePackagesResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }



    @Test
    @DisplayName("Список упаковок == null -> Ответ 400")
    void whenEmptyListPackages_thenReturn400() {
        var request = new CalculatePackagesRequest(null,"RUB",
                new Destination(57.626562, 39.893809),
                new Departure(59.938659, 30.314457));

        ResponseEntity<String> response = restTemplate.postForEntity(baseCalculateApi, request, String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
