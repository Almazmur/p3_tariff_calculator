package ru.fastdelivery.usecase;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.fastdelivery.domain.common.coordinates.Departure;
import ru.fastdelivery.domain.common.coordinates.Destination;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.price.Price;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named
@RequiredArgsConstructor
@Slf4j
public class TariffCalculatorByDistance {
    private final CoordinatesProvider coordinatesProvider;
    private static final int MIN_DISTANCE = 450;

    public Price calc(Departure departure, Destination destination, Price baseCost, Currency currency) {
        log.info("Вызван метод TariffCalculatorByDistance -> calc");
        if (!isPermissibleLatitude(departure.latitude()) || !isPermissibleLatitude(destination.latitude())) {
            log.error("Ошибка возникла в методе TariffCalculatorByDistance -> calc");
            throw new IllegalArgumentException("Latitude can't be more than " + coordinatesProvider.getMaxLatitude()
                    + " and less than " + coordinatesProvider.getMinLatitude());
        }
        if (!isPermissibleLongitude(departure.longitude()) || !isPermissibleLongitude(destination.longitude())) {
            log.error("Ошибка возникла в методе TariffCalculatorByDistance -> calc");
            throw new IllegalArgumentException("Longitude can't be more than " + coordinatesProvider.getMaxLongitude()
                    + " and less than " + coordinatesProvider.getMinLongitude());
        }

        double distanceInKM = calculateDistance(departure, destination);

        BigDecimal costOfDelivery = BigDecimal.valueOf((distanceInKM / MIN_DISTANCE) * baseCost.amount().doubleValue())
                .setScale(2, RoundingMode.CEILING);

        if (distanceInKM > MIN_DISTANCE) {
            log.info("TariffCalculatorByDistance -> calc метод вернул стоимость доставки с учетом расстояния " + costOfDelivery);
            return new Price(costOfDelivery, currency);
        }
        log.info("TariffCalculatorByDistance -> calc вернул базовую стоимость доставки " + baseCost.amount());
        return baseCost;
    }

    private double calculateDistance(Departure departure, Destination destination) {
        LatLng coordsDeparture = new LatLng(departure.latitude(),
                departure.longitude());

        LatLng coordsDestination = new LatLng(destination.latitude(),
                destination.longitude());
        double distance = LatLngTool.distance(coordsDeparture, coordsDestination, LengthUnit.KILOMETER);
        log.info("TariffCalculatorByDistance -> calculateDistance метод расчитал расстояние между пунктами: " + distance);
        return distance;
    }

    private boolean isPermissibleLatitude(double latitude) {

        return !(latitude < coordinatesProvider.getMinLatitude()) &&
                !(latitude > coordinatesProvider.getMaxLatitude());
    }

    private boolean isPermissibleLongitude(double longitude) {
        return !(longitude < coordinatesProvider.getMinLongitude()) &&
                !(longitude > coordinatesProvider.getMaxLongitude());
    }


}

