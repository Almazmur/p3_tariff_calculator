package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.properties.provider.CoordinatesProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatesPropertiesTest {
    public static final double MIN_LATITUDE = 60.398660;
    public static final double MAX_LATITUDE = 55.027532;
    public static final double MIN_LONGITUDE = 62.446008;
    public static final double MAX_LONGITUDE = 54.027532;

    CoordinatesProperties coordinatesProperties;

    @BeforeEach
    void init() {
        coordinatesProperties = new CoordinatesProperties();
        coordinatesProperties.setMinLatitude(MIN_LATITUDE);
        coordinatesProperties.setMaxLatitude(MAX_LATITUDE);
        coordinatesProperties.setMinLongitude(MIN_LONGITUDE);
        coordinatesProperties.setMaxLongitude(MAX_LONGITUDE);
    }

    @Test
    void whenCallGetMinLatitude_thenRequestFromConfig() {
        var actual = coordinatesProperties.getMinLatitude();

        assertEquals(MIN_LATITUDE, actual);
    }

    @Test
    void whenCallGetMaxLatitude_thenRequestFromConfig() {
        var actual = coordinatesProperties.getMaxLatitude();

        assertEquals(MAX_LATITUDE, actual);
    }

    @Test
    void whenCallGetMinLongitude_thenRequestFromConfig() {
        var actual = coordinatesProperties.getMinLongitude();

        assertEquals(MIN_LONGITUDE, actual);
    }

    @Test
    void whenCallGetMaxLongitude_thenRequestFromConfig() {
        var actual = coordinatesProperties.getMaxLongitude();

        assertEquals(MAX_LONGITUDE, actual);
    }
}

