package com.example.notificationsystem.controller;

import com.example.notificationsystem.dto.SubscriberDto;
import com.example.notificationsystem.dto.SubscriberRequest;
import com.example.notificationsystem.service.SubscriberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribers")
@Tag(name = "Subscriber", description = "Subscribe and Unsubscribe operations")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberService subscriberService;

    @Operation(summary = "Create a new subscriber")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriberDto subscribe(@RequestBody @Valid SubscriberRequest request) {
        return subscriberService.create(request);
    }

    @Operation(summary = "Cancel the subscription")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsubscribe(@PathVariable Long id) {
        subscriberService.delete(id);
    }
}