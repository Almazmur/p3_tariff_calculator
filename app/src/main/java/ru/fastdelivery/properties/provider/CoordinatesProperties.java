package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.usecase.CoordinatesProvider;

@ConfigurationProperties("coordinates")
@Setter
public class CoordinatesProperties implements CoordinatesProvider {
    private double minLatitude;
    private double maxLatitude;
    private double minLongitude;
    private double maxLongitude;


    @Override
    public double getMinLatitude() {
        return minLatitude;
    }

    @Override
    public double getMaxLatitude() {
        return maxLatitude;
    }

    @Override
    public double getMinLongitude() {
        return minLongitude;
    }

    @Override
    public double getMaxLongitude() {
        return maxLongitude;
    }
}