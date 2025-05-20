package com.example.notificationsystem.service;

import com.example.notificationsystem.dto.NotificationRequest;
import com.example.notificationsystem.dto.SubscriberDto;

import java.util.List;

public interface NotificationService {

    List<SubscriberDto> listSubscribers();

    void sendToAll(NotificationRequest request);
}