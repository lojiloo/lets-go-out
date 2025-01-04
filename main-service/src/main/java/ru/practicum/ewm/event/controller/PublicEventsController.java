package ru.practicum.ewm.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StatsClient;
import ru.practicum.dto.SaveStatsDto;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventPublicParamDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.EventsService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Slf4j
public class PublicEventsController {
    private final EventsService service;
    private final StatsClient client;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getAllEventsPublic(@RequestParam(required = false) String text,
                                                  @RequestParam(required = false) List<Integer> categories,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                  @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                  @RequestParam(required = false) Boolean onlyAvailable,
                                                  @RequestParam(required = false, defaultValue = "EVENT_DATE") EventPublicParamDto.Sort sort,
                                                  @RequestParam(required = false, defaultValue = "0") Integer from,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size,
                                                  HttpServletRequest request) {
        log.info("PublicEventController: пришёл запрос на получение всех событий по параметрам: " +
                        "text={}, categories={}, paid={}, rangeStart={}, rangeEnd={}, onlyAvailable={}, sort={}, from={}, size={}",
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);

        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        client.saveStats(new SaveStatsDto("ewm-main-service", uri, ip));
        log.info("Информация о запросе по uri={} от пользователя с ip={} отправлена в сервис статистики", uri, ip);

        return service.getAllEventsPublic(new EventPublicParamDto(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventById(@PathVariable Integer id, HttpServletRequest request) {
        log.info("PublicEventController: пришёл запрос на получение события по id={}", id);

        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();
        client.saveStats(new SaveStatsDto("ewm-main-service", uri, ip));
        log.info("Информация о запросе по uri={} от пользователя с ip={} отправлена в сервис статистики", uri, ip);

        return service.getEventById(id);
    }
}