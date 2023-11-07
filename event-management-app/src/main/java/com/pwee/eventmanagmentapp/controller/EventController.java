package com.pwee.eventmanagmentapp.controller;

import com.pwee.eventmanagmentapp.dto.EventCreationDto;
import com.pwee.eventmanagmentapp.dto.EventDto;
import com.pwee.eventmanagmentapp.entity.Event;
import com.pwee.eventmanagmentapp.service.EventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/event")
@AllArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable Long eventId) {
        EventDto event = eventService.getEventDtoById(eventId);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/")
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createEvent(@Valid @RequestBody EventCreationDto event, @PathVariable Long userId, BindingResult result) {
        if (result.hasErrors()) {
            return handleValidationErrors(result);
        }

        EventDto createdEvent = eventService.createEvent(event, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Long eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody EventDto event, @PathVariable Long userId, BindingResult result) {
        if (result.hasErrors()) {
            return handleValidationErrors(result);
        }

        EventDto updatedEvent = eventService.updateEvent(event, userId);
        return ResponseEntity.ok(updatedEvent);
    }

    private ResponseEntity<?> handleValidationErrors(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}