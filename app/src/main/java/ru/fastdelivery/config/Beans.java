package ru.fastdelivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.currency.CurrencyPropertiesProvider;
import ru.fastdelivery.usecase.*;

/**
 * Определение реализаций бинов для всех модулей приложения
 */
@Configuration
public class Beans {

    @Bean
    public CurrencyFactory currencyFactory(CurrencyPropertiesProvider currencyProperties) {
        return new CurrencyFactory(currencyProperties);
    }

    @Bean
    public TariffCalculatorByWeight tariffCalculateUseCase(WeightPriceProvider weightPriceProvider) {
        return new TariffCalculatorByWeight(weightPriceProvider);
    }

    @Bean
    public TariffCalculatorByDimensions tariffCalculatorByDimensions(DimensionsPriseProvider dimensionsPriseProvider) {
        return new TariffCalculatorByDimensions(dimensionsPriseProvider);
    }

    @Bean
    public TariffCalculatorForBasicCost tariffCalculatorForBasicCost() {
        return new TariffCalculatorForBasicCost();
    }

    @Bean
    public TariffCalculatorByDistance tariffCalculatorByDistance(CoordinatesProvider coordinatesProvider) {
        return new TariffCalculatorByDistance(coordinatesProvider);
    }
}
