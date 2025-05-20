package com.example.notificationsystem.service;

import com.example.notificationsystem.dto.SubscriberDto;
import com.example.notificationsystem.dto.SubscriberRequest;

import java.util.List;

public interface SubscriberService {
    SubscriberDto create(SubscriberRequest request);

    void delete(Long id);

    List<SubscriberDto> findAll();
}