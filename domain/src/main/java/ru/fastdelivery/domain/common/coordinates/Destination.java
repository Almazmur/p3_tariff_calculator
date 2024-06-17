package ru.fastdelivery.domain.common.coordinates;
/**
 * Класс координат места назначения
 *
 * @param latitude значение широты
 * @param longitude валюта долготы
 *
 */
public record Destination(
        double latitude,
        double longitude) {

}