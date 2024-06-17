package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.dimension.Dimensions;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {
    Dimensions dimensions1;
    Dimensions dimensions2;
    Weight weight1;
    Weight weight2;
    List<Pack> packages;
    Shipment shipment;

    @BeforeEach
    void init() {
        dimensions1 = new Dimensions(BigDecimal.valueOf(300), BigDecimal.valueOf(500), BigDecimal.valueOf(200));
        dimensions2 = new Dimensions(BigDecimal.valueOf(400), BigDecimal.valueOf(600), BigDecimal.valueOf(300));
        weight1 = new Weight(BigInteger.TEN);
        weight2 = new Weight(BigInteger.ONE);
        packages = List.of(new Pack(weight1, dimensions1), new Pack(weight2, dimensions2));
        shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"));

    }

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }

    @Test
    void whenSummarizingVolumeAllPackages_thenReturnOverallVolume() {
        var totalShipmentVolume = shipment.totalVolumeAllPackages();

        assertThat(totalShipmentVolume).isEqualByComparingTo(BigDecimal.valueOf(0.102));
    }
}