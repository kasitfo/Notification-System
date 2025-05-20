package com.example.notificationsystem.service;

import com.example.notificationsystem.dto.NotificationRequest;
import com.example.notificationsystem.dto.SubscriberDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private SubscriberService subscriberService;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private List<SubscriberDto> mockSubscribers;

    @BeforeEach
    void setUp() {
        mockSubscribers = List.of(
                new SubscriberDto(1L, "test1@example.com", Instant.now()),
                new SubscriberDto(2L, "test2@example.com", Instant.now())
        );
    }

    @Test
    void listSubscribers_shouldReturnAllFromSubscriberService() {
        when(subscriberService.findAll()).thenReturn(mockSubscribers);

        List<SubscriberDto> result = notificationService.listSubscribers();

        assertThat(result).isSameAs(mockSubscribers);
        verify(subscriberService).findAll();
    }

    @Test
    void sendToAll_shouldFetchSubscribersAndLogNotification() {
        when(subscriberService.findAll()).thenReturn(mockSubscribers);
        NotificationRequest request = new NotificationRequest("Alert", "Test notification message");

        notificationService.sendToAll(request);

        verify(subscriberService).findAll();
    }
}