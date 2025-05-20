package com.example.notificationsystem.service;

import com.example.notificationsystem.dto.SubscriberDto;
import com.example.notificationsystem.dto.SubscriberRequest;
import com.example.notificationsystem.model.Subscriber;
import com.example.notificationsystem.repository.SubscriberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubscriberServiceImpl implements SubscriberService {

    SubscriberRepository subscriberRepository;

    @Override
    public SubscriberDto create(SubscriberRequest request) {
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(request.getEmail());
        subscriber = subscriberRepository.save(subscriber);
        return mapToDto(subscriber);
    }

    @Override
    public void delete(Long id) {
        Subscriber subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscriber not found with id: " + id));
        subscriberRepository.delete(subscriber);
    }

    @Override
    public List<SubscriberDto> findAll() {
        return subscriberRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    private SubscriberDto mapToDto(Subscriber subscriber) {
        SubscriberDto dto = new SubscriberDto();
        dto.setId(subscriber.getId());
        dto.setEmail(subscriber.getEmail());
        dto.setSubscribedAt(subscriber.getSubscribedAt());
        return dto;
    }
}