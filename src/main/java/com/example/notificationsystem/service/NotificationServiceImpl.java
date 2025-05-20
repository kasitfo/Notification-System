package com.example.notificationsystem.service;

import com.example.notificationsystem.dto.NotificationRequest;
import com.example.notificationsystem.dto.SubscriberDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private final SubscriberService subscriberService;

    @Override
    public List<SubscriberDto> listSubscribers() {
        return subscriberService.findAll();
    }

    @Override
    public void sendToAll(NotificationRequest request) {
        List<SubscriberDto> subscribers = subscriberService.findAll();
        subscribers.forEach(sub -> log.info("Sending notification '{}' with message '{}' to {}",
                request.getTitle(), request.getMessage(), sub.getEmail()));
    }
}