package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.controllers.rest.mapper.CounterpartyCreateMapper;
import com.profcut.ordermanager.domain.dto.counterparty.CounterpartyFieldsPatch;
import com.profcut.ordermanager.domain.dto.counterparty.CreateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.counterparty.UpdateCounterpartyRequest;
import com.profcut.ordermanager.domain.dto.filter.FilterRequest;
import com.profcut.ordermanager.domain.dto.filter.PageRequest;
import com.profcut.ordermanager.domain.entities.CounterpartyEntity;
import com.profcut.ordermanager.domain.exceptions.CounterpartyNotFoundException;
import com.profcut.ordermanager.domain.repository.CounterpartyRepository;
import com.profcut.ordermanager.utils.jpa.specification.CounterpartySpecification;
import com.profcut.ordermanager.utils.jpa.specification.PageConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CounterpartyServiceTest {

    @Mock
    CounterpartyRepository counterpartyRepository;
    @Mock
    CounterpartyCreateMapper counterpartyCreateMapper;
    @InjectMocks
    CounterpartyServiceImpl counterpartyService;

    @Test
    @DisplayName("Поиск контрагента по id")
    void findCounterparty() {
        var id = UUID.randomUUID();
        var entity = new CounterpartyEntity().setId(id).setName("name");

        when(counterpartyRepository.findCounterpartyById(id)).thenReturn(Optional.of(entity));

        var result = counterpartyService.findById(id);

        assertNotNull(result);
        verify(counterpartyRepository).findCounterpartyById(id);
    }

    @Test
    @DisplayName("Ошибка поиска контрагента по id")
    void findCounterparty_not_found() {
        var id = UUID.randomUUID();

        when(counterpartyRepository.findCounterpartyById(id)).thenReturn(Optional.empty());

        assertThatCode(() -> counterpartyService.findById(id)).isInstanceOf(CounterpartyNotFoundException.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    @DisplayName("Получение контрагента по фильтра")
    void findCounterpartiesByFilter() {
        var filter = FilterRequest.builder()
                .search("name")
                .pageRequest(new PageRequest(0, 20))
                .build();
        var counterparties = Stream.of("name1", "name2")
                .map(name -> new CounterpartyEntity()
                        .setId(UUID.randomUUID())
                        .setName(name))
                .toList();
        var spec = CounterpartySpecification.byCounterpartyNameLike(filter.search);
        var pageable = PageConverter.covertToPageable(filter.getPageRequest());
        Page<CounterpartyEntity> counterpartiesPage = new PageImpl<>(counterparties, pageable, counterparties.size());

        when(counterpartyRepository.findAll(any(spec.getClass()), any(Pageable.class))).thenReturn(counterpartiesPage);

        assertThatCode(() -> counterpartyService.findCounterpartiesByFilter(filter)).doesNotThrowAnyException();

        verify(counterpartyRepository).findAll(any(spec.getClass()), any(Pageable.class));
    }

    @Test
    @DisplayName("Получение страницы контрагента")
    void getCounterpartyPage() {
        var request = new PageRequest().setPage(0).setSize(20);

        assertThatCode(() -> counterpartyService.getCounterpartiesPage(request)).doesNotThrowAnyException();

        verify(counterpartyRepository).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Успешное создание контрагента")
    void createCounterparty() {
        var request = CreateCounterpartyRequest.builder()
                .name("name").fullName("fullName")
                .email("test@mail.ru").build();
        var entity = new CounterpartyEntity().setName(request.getName())
                .setFullName(request.getFullName()).setEmail(request.getEmail());

        when(counterpartyCreateMapper.apply(request)).thenReturn(entity);
        when(counterpartyRepository.save(entity)).thenReturn(entity.setId(UUID.randomUUID()));

        var result = counterpartyService.createCounterparty(request);

        assertNotNull(result);
        verify(counterpartyCreateMapper).apply(request);
        verify(counterpartyRepository).save(entity);
    }

    @Test
    @DisplayName("Удаление контрагента")
    void deleteCounterparty() {
        var id = UUID.randomUUID();

        assertThatCode(() -> counterpartyService.deleteCounterpartyById(id)).doesNotThrowAnyException();

        verify(counterpartyRepository).deleteById(id);
    }

    @Test
    @DisplayName("Обновление контрагента")
    void updateCounterparty() {
        var id = UUID.randomUUID();
        var updateRequest = new UpdateCounterpartyRequest()
                .setId(id)
                .setPatch(CounterpartyFieldsPatch.builder().name("newName").build());
        var entity = new CounterpartyEntity().setId(id).setName(updateRequest.getPatch().getName());

        when(counterpartyRepository.findCounterpartyById(id)).thenReturn(Optional.ofNullable(entity));

        assertThatCode(() -> counterpartyService.updateCounterparty(updateRequest)).doesNotThrowAnyException();

        verify(counterpartyRepository).findCounterpartyById(id);
        verify(counterpartyRepository).save(any(CounterpartyEntity.class));
    }
}
