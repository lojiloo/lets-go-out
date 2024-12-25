package ru.practicum;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import ru.practicum.dto.ReturnStatsDto;
import ru.practicum.dto.SaveStatsDto;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class StatsClient {
    protected final WebClient client = WebClient.create();
    protected final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public void saveStats(SaveStatsDto body, String path) {
        client.post()
                .uri(path + "/hit")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<ReturnStatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique, String path) {
        String startEncoded = URLEncoder.encode(start.format(formatter), StandardCharsets.UTF_8);
        String endEncoded = URLEncoder.encode(end.format(formatter), StandardCharsets.UTF_8);

        return client.get()
                .uri(builder -> builder.path(path + "/stats")
                        .queryParam("start", startEncoded)
                        .queryParam("end", endEncoded)
                        .queryParamIfPresent("uris", Optional.of(uris))
                        .queryParam("unique", unique).build())
                .retrieve()
                .bodyToFlux(ReturnStatsDto.class)
                .collectList()
                .block();
    }
}
