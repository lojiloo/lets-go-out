package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.practicum.dao.StatsRepository;
import ru.practicum.dto.ReturnStatsDto;
import ru.practicum.dto.SaveStatsDto;
import ru.practicum.model.Action;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    private final StatsRepository repository;
    private final ModelMapper mapper;

    @Override
    public void saveStats(SaveStatsDto request) {
        LocalDateTime timestamp = LocalDateTime.now();

        Action action = mapper.map(request, Action.class);
        action.setTimestamp(LocalDateTime.parse(formatter.format(timestamp)));

        repository.save(action);
    }

    @Override
    public List<ReturnStatsDto> getStats(String startDecoded, String endDecoded, List<String> uris, boolean unique) {
        LocalDateTime start = LocalDateTime.parse(startDecoded);
        LocalDateTime end = LocalDateTime.parse(endDecoded);

        if (unique) {
            if (!uris.isEmpty()) {
                return repository.getStatsByTimestampBetweenAndUriInAndIpUnique(start, end, uris);
            } else {
                return repository.getStatsByTimestampBetweenAndIpUnique(start, end);
            }
        } else {
            if (!uris.isEmpty()) {
                return repository.getStatsByTimestampBetweenAndUriIn(start, end, uris);
            } else {
                return repository.getStatsByTimestampBetween(start, end);
            }
        }
    }

}
