package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;

/**
 * Расчет цены на основе объема
 */
@Named
@RequiredArgsConstructor
@Slf4j
public class TariffCalculatorByDimensions {

    private final DimensionsPriseProvider dimensionsPriseProvider;

    public Price calc(Shipment shipment) {
        log.info("Вызван метод TariffCalculatorByDimensions -> calc");
        var totalVolumeAllPackages = shipment.totalVolumeAllPackages();
        var minimalPrice = dimensionsPriseProvider.minimalPrice();

        return dimensionsPriseProvider
                .costPerCubicMeter()
                .multiply(totalVolumeAllPackages)
                .max(minimalPrice);
    }


}
