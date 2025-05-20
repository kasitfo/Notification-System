package com.example.notificationsystem.service;

import com.example.notificationsystem.dto.SubscriberDto;
import com.example.notificationsystem.dto.SubscriberRequest;
import com.example.notificationsystem.model.Subscriber;
import com.example.notificationsystem.repository.SubscriberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriberServiceImplTest {

    @Mock
    private SubscriberRepository repository;

    @InjectMocks
    private SubscriberServiceImpl service;

    private Subscriber exampleEntity;

    @BeforeEach
    void setUp() {
        exampleEntity = new Subscriber();
        exampleEntity.setId(1L);
        exampleEntity.setEmail("test@example.com");
        exampleEntity.setSubscribedAt(Instant.now());
    }

    @Test
    void create_shouldSaveEntityAndReturnDto() {
        var request = new SubscriberRequest("test@example.com");

        when(repository.save(any(Subscriber.class)))
                .thenAnswer(invocation -> {
                    Subscriber s = invocation.getArgument(0);
                    s.setId(1L);
                    s.setSubscribedAt(Instant.ofEpochMilli(1000));
                    return s;
                });

        SubscriberDto dto = service.create(request);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getEmail()).isEqualTo("test@example.com");
        assertThat(dto.getSubscribedAt()).isEqualTo(Instant.ofEpochMilli(1000));

        ArgumentCaptor<Subscriber> captor = ArgumentCaptor.forClass(Subscriber.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void findAll_shouldReturnAllDtos() {
        Subscriber other = new Subscriber(2L, "other@ex.com", Instant.ofEpochMilli(2000));
        when(repository.findAll()).thenReturn(List.of(exampleEntity, other));

        List<SubscriberDto> dtos = service.findAll();

        assertThat(dtos).hasSize(2)
                .extracting(SubscriberDto::getEmail)
                .containsExactly("test@example.com", "other@ex.com");
        verify(repository).findAll();

    }

    @Test
    void delete_existingId_shouldDelete() {
        when(repository.findById(1L)).thenReturn(Optional.of(exampleEntity));
        service.delete(1L);
        verify(repository).delete(exampleEntity);
    }

    @Test
    void delete_nonExistingId_shouldThrowNotFound() {
        when(repository.findById(42L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.delete(42L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Subscriber not found with id: 42");
        verify(repository, never()).delete(any());
    }
}