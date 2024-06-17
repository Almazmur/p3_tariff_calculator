package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,

        @Schema(description = "Длина упаковки, миллиметры", example = "345.45")
        BigDecimal length,

        @Schema(description = "Ширина упаковки, миллиметры", example = "589.45")
        BigDecimal width,

        @Schema(description = "Высота упаковки, миллиметры", example = "234.45")
        BigDecimal height
) {
}