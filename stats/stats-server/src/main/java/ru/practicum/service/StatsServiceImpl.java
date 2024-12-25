package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.dao.StatsRepository;
import ru.practicum.dto.QueryResultDto;
import ru.practicum.dto.ReturnStatsDto;
import ru.practicum.dto.SaveStatsDto;
import ru.practicum.model.Action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatsServiceImpl implements StatsService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final StatsRepository repository;
    private final ModelMapper mapper;

    @Override
    public void saveStats(SaveStatsDto request) {
        LocalDateTime timestamp = LocalDateTime.now();

        Action action = mapper.map(request, Action.class);
        action.setTimestamp(timestamp);
        log.info("Создан объект ACTION с полями: app={}, uri={}, ip={}, timestamp={}", action.getApp(), action.getUri(), action.getIp(), action.getTimestamp());

        repository.save(action);
    }

    @Override
    public List<ReturnStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        List<QueryResultDto> statsQueryResult;
        if (unique) {
            if (!uris.isEmpty()) {
                log.info("Пришёл запрос на получение выборки по уникальным IP. Параметр uris не пуст: {}", uris);
                statsQueryResult = repository.getStatsByTimestampBetweenAndUriInAndIpUnique(start, end, uris);
            } else {
                log.info("Пришёл запрос на получение выборки по уникальным IP. Параметр uris пуст");
                statsQueryResult = repository.getStatsByTimestampBetweenAndIpUnique(start, end);
            }
        } else {
            if (!uris.isEmpty()) {
                log.info("Пришёл запрос на получение выборки. Параметр uris не пуст: {}", uris);
                statsQueryResult = repository.getStatsByTimestampBetweenAndUriIn(start, end, uris);
            } else {
                log.info("Пришёл запрос на получение выборки. Параметр uris пуст");
                statsQueryResult = repository.getStatsByTimestampBetween(start, end);
            }
        }

        return statsQueryResult.stream()
                .map(queryResultDto -> mapper.map(queryResultDto, ReturnStatsDto.class))
                .toList();
    }

}
