package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.fastdelivery.domain.common.coordinates.Departure;
import ru.fastdelivery.domain.common.coordinates.Destination;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
        @Schema(description = "Список упаковок отправления",
                example = """
                        [{"weight": 4056.45,
                        "length": 345,
                        "width": 589,
                        "height": 234}]
                        """)
        @NotNull
        @NotEmpty
        List<CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull
        String currencyCode,

        @Schema(description = "Координаты места назначения",
                example = """
                        "latitude" : 73.398660,
                        "longitude" : 55.027532
                        """)
        @NotNull
        Destination destination,

        @Schema(description = "Координаты отправления",
                example = """
                        "latitude" : 55.446008,
                        "longitude" : 65.339151
                        """)
        @NotNull
        Departure departure
) {
}