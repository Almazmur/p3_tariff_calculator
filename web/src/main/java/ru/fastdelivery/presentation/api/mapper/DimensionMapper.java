package ru.fastdelivery.presentation.api.mapper;

import ru.fastdelivery.domain.common.dimension.Dimensions;
import ru.fastdelivery.presentation.api.request.CargoPackage;

/**
 * Преобразование CargoPackage в Dimensions
 */
public class DimensionMapper {

    public static Dimensions cargoPackageToDimension(CargoPackage cargoPackage) {

        return new Dimensions(cargoPackage.length(), cargoPackage.width(), cargoPackage.height());

    }
}