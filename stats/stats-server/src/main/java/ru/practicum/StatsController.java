package ru.practicum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ReturnStatsDto;
import ru.practicum.dto.SaveStatsDto;
import ru.practicum.service.StatsServiceImpl;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class StatsController {
    private final StatsServiceImpl service;

    @PostMapping(path = "/hit")
    @ResponseStatus(value = HttpStatus.CREATED, reason = "Информация сохранена")
    public void saveStats(@RequestBody SaveStatsDto request) {
        service.saveStats(request);
    }

    @GetMapping(path = "/stats")
    @ResponseStatus(value = HttpStatus.OK, reason = "Статистика собрана")
    public List<ReturnStatsDto> getStats(@RequestParam String startEncoded,
                                         @RequestParam String endEncoded,
                                         @RequestParam(required = false) String[] uris,
                                         @RequestParam(defaultValue = "false") boolean unique) {
        String startDecoded = URLDecoder.decode(startEncoded, StandardCharsets.UTF_8);
        String endDecoded = URLDecoder.decode(endEncoded, StandardCharsets.UTF_8);

        return service.getStats(startDecoded, endDecoded, Arrays.asList(uris), unique);
    }

}
