package ru.practicum.ewm.event.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.service.EventsService;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
@Slf4j
public class PrivateEventsController {
    private final EventsService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addNewEventPrivate(@RequestBody @Valid NewEventDto request,
                                           @PathVariable Integer userId) {
        log.info("PrivateEventsController: пришёл запрос на добавление нового события {} от пользователя {}", request, userId);
        return service.addNewEventPrivate(request, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getUsersEvents(@PathVariable Integer userId,
                                              @RequestParam(required = false, defaultValue = "0") @PositiveOrZero Integer from,
                                              @RequestParam(required = false, defaultValue = "10") @Positive Integer size) {
        log.info("PrivateEventsController: пришёл запрос на получение собственных событий от пользователя {}. Пагинация: from={}, size={}", userId, from, size);
        return service.getUsersEvents(userId, from, size);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getUsersEventByEventId(@PathVariable Integer userId,
                                               @PathVariable Integer eventId) {
        log.info("PrivateEventsController: пришёл запрос на получение полной информации о событии c id={}, добавленном пользователем {}", userId, eventId);
        return service.getUsersEventById(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getUsersEventRequestsByEventId(@PathVariable Integer userId,
                                                                        @PathVariable Integer eventId) {
        log.info("PrivateEventsController: пришёл запрос на получение ифнормации о запросах на участие в событии с id={} пользователя c id={}", eventId, userId);
        return service.getUsersEventRequestsByEventId(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateUsersEventPrivate(@RequestBody @Valid UpdateEventUserRequest request,
                                                @PathVariable Integer userId,
                                                @PathVariable Integer eventId) {
        log.info("PrivateEventsController: пришёл запрос на изменение события c id={} от пользователя с id={}. Тело запроса: {}", eventId, userId, request);
        return service.updateUsersEventPrivate(request, userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateUsersEventRequestsByEventId(@RequestBody @Valid EventRequestStatusUpdateRequest request,
                                                                            @PathVariable Integer userId,
                                                                            @PathVariable Integer eventId) {
        log.info("PrivateEventsController: пришёл запрос на изменение статуса заявок с id={} на статус {}", request.getRequestIds(), request.getStatus());
        return service.updateUsersEventRequestsByEventId(request, userId, eventId);
    }

}