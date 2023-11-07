package com.pwee.eventmanagmentapp.service;

import com.pwee.eventmanagmentapp.dto.EventCreationDto;
import com.pwee.eventmanagmentapp.dto.EventDto;
import com.pwee.eventmanagmentapp.entity.Event;
import com.pwee.eventmanagmentapp.entity.User;
import com.pwee.eventmanagmentapp.exception.EventNotFoundException;
import com.pwee.eventmanagmentapp.exception.UserNotFoundException;
import com.pwee.eventmanagmentapp.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    public EventDto getEventDtoById(Long eventId) {
        return eventRepository
                .findById(eventId)
                .map(event -> new EventDto(
                        event.getId(),
                        event.getName(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getDescription(),
                        event.getLink()))
                .orElseThrow(() -> new EventNotFoundException("Event doesn't exist!"));
    }

//    TODO
//    public List<EventDto> getAllUserEvents(Long userId) {}
    public List<EventDto> getAllEvents() {
        return eventRepository
                .findAll()
                .stream()
                .map(event -> new EventDto(
                        event.getId(),
                        event.getName(),
                        event.getStartDate(),
                        event.getEndDate(),
                        event.getDescription(),
                        event.getLink()))
                .collect(Collectors.toList());
    }
    public EventDto createEvent(EventCreationDto eventCreationDto, Long userId) {
        User user = userService.getUserById(userId);

        Event event = Event
                .builder()
                .user(user)
                .name(eventCreationDto.getName())
                .startDate(eventCreationDto.getStartDate())
                .endDate(eventCreationDto.getEndDate())
                .description(eventCreationDto.getDescription())
                .link(eventCreationDto.getLink())
                .build();

        Event createdEvent = eventRepository.save(event);
        user.getEvents().add(createdEvent);
        userService.updateUser(user);

        return EventDto
                .builder()
                .id(createdEvent.getId())
                .name(createdEvent.getName())
                .startDate(createdEvent.getStartDate())
                .endDate(createdEvent.getEndDate())
                .description(createdEvent.getDescription())
                .link(createdEvent.getLink())
                .build();
    }

    public EventDto updateEvent(EventDto modifiedEvent, Long userId) {
        User user = userService.getUserById(userId);

        Event eventToUpdate = eventRepository.findById(modifiedEvent.getId())
                .orElseThrow(() -> new EventNotFoundException("Event doesn't exist! Nothing updated."));

        user
                .getEvents()
                .stream()
                .filter(event -> modifiedEvent.getId().equals(eventToUpdate.getId()))
                .findAny()
                .ifPresent(event -> user.getEvents().remove(event));

        Event updatedEvent = Event
                .builder()
                .id(modifiedEvent.getId())
                .name(modifiedEvent.getName())
                .startDate(modifiedEvent.getStartDate())
                .endDate(modifiedEvent.getEndDate())
                .description(modifiedEvent.getDescription())
                .link(modifiedEvent.getLink())
                .build();

        user.getEvents().add(updatedEvent);
        userService.updateUser(user);

        return modifiedEvent;
    }

    public void deleteEventById(Long eventId) {
        Event eventToDelete = getEventById(eventId);
        User user = userService.getUserById(eventToDelete.getUser().getId());
        user.getEvents().remove(eventToDelete);
        userService.updateUser(user);

        eventRepository.delete(eventToDelete);
    }

    public Event getEventById(Long eventId) {
        return eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event doesn't exist!"));
    }
}
