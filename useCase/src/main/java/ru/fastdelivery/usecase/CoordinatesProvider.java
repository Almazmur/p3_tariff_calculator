package ru.fastdelivery.usecase;

public interface CoordinatesProvider {
    double getMinLatitude();

    double getMaxLatitude();

    double getMinLongitude();

    double getMaxLongitude();
}
