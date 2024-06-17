package ru.fastdelivery.domain.common.coordinates;

/**
 * Класс координат точки отправления
 *
 * @param latitude значение широты
 * @param longitude валюта долготы
 *
 */

public record Departure(
        double latitude,
        double longitude) {
}