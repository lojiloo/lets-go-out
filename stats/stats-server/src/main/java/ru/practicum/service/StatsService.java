package ru.practicum.service;

import ru.practicum.dto.ReturnStatsDto;
import ru.practicum.dto.SaveStatsDto;

import java.util.List;

public interface StatsService {

    void saveStats(SaveStatsDto request);

    List<ReturnStatsDto> getStats(String startDecoded, String endDecoded, List<String> uris, boolean unique);

}
