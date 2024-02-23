package com.profcut.ordermanager.service.impl;

import com.profcut.ordermanager.domain.entities.TechnologistEntity;
import com.profcut.ordermanager.domain.exceptions.TechnologistNotFoundException;
import com.profcut.ordermanager.domain.repository.TechnologistRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TechnologistServiceImplTest {

    @Mock
    TechnologistRepository repository;
    @InjectMocks
    TechnologistServiceImpl technologistService;

    @Test
    void should_return_technologist_by_name() {
        var fullName = "Ф.И.О.";
        var technologist = new TechnologistEntity().setId(UUID.randomUUID())
                .setFullName(fullName)
                .setPhone("0123")
                .setEmail("email");

        when(repository.findTechnologistByName(any())).thenReturn(Optional.of(technologist));

        assertThatCode(() -> technologistService.findByName(fullName)).doesNotThrowAnyException();

        verify(repository).findTechnologistByName(any());
    }

    @Test
    void should_return_exception_when_technologist_by_name() {
        var fullName = "Ф.И.О.";

        assertThatCode(() -> technologistService.findByName(fullName)).isInstanceOf(TechnologistNotFoundException.class);

        verify(repository).findTechnologistByName(any());
    }
}
