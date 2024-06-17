package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
@Slf4j
public class TariffCalculatorByWeight {
    private final WeightPriceProvider weightPriceProvider;

    public Price calc(Shipment shipment) {
        log.info("Вызван метод TariffCalculatorByWeight -> calc");
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalPrice = weightPriceProvider.minimalPrice();
        log.info("TariffCalculatorByWeight -> minimalPrice. Минимальная цена: " + minimalPrice.amount());
        return weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPrice);
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
