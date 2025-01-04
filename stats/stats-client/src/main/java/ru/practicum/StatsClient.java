package ru.practicum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.dto.ReturnStatsDto;
import ru.practicum.dto.SaveStatsDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class StatsClient {
    protected final WebClient client;

    @Autowired
    public StatsClient(String uri) {
        this.client = WebClient.builder().baseUrl(uri).build();
    }

    public void saveStats(SaveStatsDto body) {
        client.post()
                .uri("/hit")
                .bodyValue(body)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<ReturnStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        return client.get()
                .uri(builder -> builder.path("/stats")
                        .queryParam("start", start)
                        .queryParam("end", end)
                        .queryParamIfPresent("uris", Optional.of(uris))
                        .queryParam("unique", unique).build())
                .retrieve()
                .bodyToFlux(ReturnStatsDto.class)
                .collectList()
                .block();
    }
}
