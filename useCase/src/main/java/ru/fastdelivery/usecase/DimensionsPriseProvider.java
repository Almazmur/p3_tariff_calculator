package ru.fastdelivery.usecase;

import ru.fastdelivery.domain.common.price.Price;

public interface DimensionsPriseProvider {
    Price costPerCubicMeter();

    Price minimalPrice();
}

