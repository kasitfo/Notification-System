package com.example.notificationsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberDto {
    private Long id;
    private String email;
    private Instant subscribedAt;
}