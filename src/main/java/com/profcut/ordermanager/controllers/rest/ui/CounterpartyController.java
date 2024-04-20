package com.profcut.ordermanager.controllers.rest.ui;

import com.profcut.ordermanager.controllers.rest.mapper.UiCounterpartyMapper;
import com.profcut.ordermanager.controllers.rest.mapper.UiCounterpartyShortMapper;
import com.profcut.ordermanager.domain.dto.counterparty.CreateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.counterparty.UiCounterparty;
import com.profcut.ordermanager.domain.dto.counterparty.UiCounterpartyShort;
import com.profcut.ordermanager.domain.dto.counterparty.UpdateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.service.CounterpartyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ui/counterparties")
@Tag(name = "counterparty-ui-api", description = "Контроллер для управления информации об контрагентов")
public class CounterpartyController {

    private final UiCounterpartyMapper uiCounterpartyMapper;
    private final UiCounterpartyShortMapper uiCounterpartyShortMapper;
    private final CounterpartyService counterpartyService;

    @GetMapping("/{counterpartyId}")
    @Operation(description = "Поиск контрагента по id")
    public UiCounterparty getCounterparty(@PathVariable UUID counterpartyId) {
        return uiCounterpartyMapper.apply(counterpartyService.findById(counterpartyId));
    }

    @PostMapping("/find")
    @Operation(description = "Поиск контрагентов по наименованию")
    public Page<UiCounterpartyShort> findCounterpartyByFilter(@RequestBody FilterRequest filter) {
        return counterpartyService.findCounterpartiesByFilter(filter).map(uiCounterpartyShortMapper);
    }

    @PostMapping("/page")
    @Operation(description = "Получить страницу контрагентов")
    public Page<UiCounterpartyShort> getCounterpartyPage(@RequestBody PageRequest request) {
        return counterpartyService.getCounterpartiesPage(request).map(uiCounterpartyShortMapper);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(description = "Создание контрагента")
    public UiCounterparty createCounterparty(@Valid @RequestBody CreateCounterpartyRequest request) {
        return uiCounterpartyMapper.apply(counterpartyService.createCounterparty(request));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Обновить информацию о контрагенте")
    public UiCounterparty updateCounterparty(@Valid @RequestBody UpdateCounterpartyRequest request) {
        return uiCounterpartyMapper.apply(counterpartyService.updateCounterparty(request));
    }

    @DeleteMapping("/{counterpartyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(description = "Удалить контрагента")
    public void deleteCounterparty(@PathVariable UUID counterpartyId) {
        counterpartyService.deleteCounterpartyById(counterpartyId);
    }
}
