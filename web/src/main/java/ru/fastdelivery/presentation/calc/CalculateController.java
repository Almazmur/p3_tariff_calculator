package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.mapper.DimensionMapper;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculatorByDimensions;
import ru.fastdelivery.usecase.TariffCalculatorByDistance;
import ru.fastdelivery.usecase.TariffCalculatorByWeight;
import ru.fastdelivery.usecase.TariffCalculatorForBasicCost;


@Slf4j
@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculatorForBasicCost tariffCalculatorForBasicCost;
    private final TariffCalculatorByWeight tariffCalculatorByWeight;
    private final TariffCalculatorByDimensions tariffCalculatorByDimensions;
    private final TariffCalculatorByDistance tariffCalculatorByDistance;
    private final CurrencyFactory currencyFactory;

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {
        log.info("Вызван метод контроллера CalculateController -> calculate");
        var packs = request.packages().stream()
                .map(i -> new Pack(new Weight(i.weight()), DimensionMapper.cargoPackageToDimension(i)))
                .toList();

        var currency = currencyFactory.create(request.currencyCode());
        var shipment = new Shipment(packs, currency);

        var basicPrice = tariffCalculatorForBasicCost.calc(
                tariffCalculatorByWeight,
                tariffCalculatorByDimensions,
                shipment
        );

        var priceIncludingDistance = tariffCalculatorByDistance.calc(
                request.departure(),
                request.destination(),
                basicPrice,
                currency
        );

        return new CalculatePackagesResponse(priceIncludingDistance, tariffCalculatorByWeight.minimalPrice());
    }
}