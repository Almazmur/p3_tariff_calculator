package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

/**
 * Расчет базовой цены методом выбора большей между ценой то весу и по габаритам
 */
@RequiredArgsConstructor
@Slf4j
public class TariffCalculatorForBasicCost {

    public Price calc(TariffCalculatorByWeight tariffCalculateByWeight,
                      TariffCalculatorByDimensions tariffCalculatorByDimensions,
                      Shipment shipment) {
        log.info("Вызван метод TariffCalculatorForBasicCost -> calc");
        var calculatedPriceByWeight = tariffCalculateByWeight.calc(shipment);
        var calculatedPriceByDimensions = tariffCalculatorByDimensions.calc(shipment);
        log.info("TariffCalculatorForBasicCost -> calc. Цена на основе веса: " + calculatedPriceByWeight.amount());
        log.info("TariffCalculatorForBasicCost -> calc. Цена на основе габаритов: " + calculatedPriceByDimensions.amount());
        return calculatedPriceByWeight.compareTo(calculatedPriceByDimensions) >= 0 ?
                calculatedPriceByWeight : calculatedPriceByDimensions;
    }

}