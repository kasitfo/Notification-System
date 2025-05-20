package com.example.notificationsystem.controller;

import com.example.notificationsystem.dto.NotificationRequest;
import com.example.notificationsystem.dto.SubscriberDto;
import com.example.notificationsystem.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Publisher", description = "List subscribers and send notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "List all subscribers")
    @GetMapping("/subscribers")
    public ResponseEntity<List<SubscriberDto>> listAllSubscribers() {
        List<SubscriberDto> subscribers = notificationService.listSubscribers();
        return ResponseEntity.ok(subscribers);
    }

    @Operation(summary = "Send a notification to all active subscribers")
    @PostMapping("/notifications")
    public ResponseEntity<Void> sendNotification(@RequestBody @Valid NotificationRequest request) {
        notificationService.sendToAll(request);
        return ResponseEntity.accepted().build();
    }
}
