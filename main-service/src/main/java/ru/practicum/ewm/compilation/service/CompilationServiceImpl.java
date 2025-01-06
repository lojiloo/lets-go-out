package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.StatsClient;
import ru.practicum.dto.ReturnStatsDto;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationRequest;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.error.exceptions.EntityExistsException;
import ru.practicum.ewm.error.exceptions.NotFoundException;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventsRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {
    private final ModelMapper mapper;
    private final StatsClient client;

    private final CompilationRepository compilationRepository;
    private final EventsRepository eventsRepository;

    @Override
    public CompilationDto addNewCompilation(NewCompilationDto request) {
        if (compilationRepository.findByTitle(request.getTitle()).isPresent()) {
            log.error("Имя новой подборки должно быть уникально. Подборка с name={} уже существует", request.getTitle());
            throw new EntityExistsException("Подборка с таким названием уже существует");
        }


        Compilation compilation = new Compilation();
        compilation.setTitle(request.getTitle());
        compilation.setIsPinned(request.getPinned() != null && request.getPinned());

        CompilationDto dto = mapper.map(compilation, CompilationDto.class);

        if (request.getEvents() != null) {
            List<Event> events = eventsRepository.findAllById(request.getEvents());
            compilation.setEvents(events);

            List<EventShortDto> eventsDtos = mapToShortEvents(events);
            dto.setEvents(eventsDtos);
        }

        compilationRepository.save(compilation);
        log.info("Создана подборка с id={}: {}", compilation.getId(), compilation);
        dto.setId(compilation.getId());

        return dto;
    }

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = PageRequest.of(from, size);

        return compilationRepository.findAllByIsPinned(pinned, pageable)
                .stream()
                .map(comp -> mapper.map(comp, CompilationDto.class))
                .toList();
    }

    @Override
    public CompilationDto getCompilationById(Integer compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id=%d was not found", compId))
        );
        log.info("Получена подборка по id={}: {}", compId, compilation);

        return mapper.map(compilation, CompilationDto.class);
    }

    @Override
    public CompilationDto updateCompilationById(UpdateCompilationRequest request, int id) {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id=%d was not found", id))
        );

        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
            log.info("Подборке присвоено новое название: title={}", request.getTitle());
        }
        if (request.getEvents() != null) {
            List<Event> events = eventsRepository.findAllById(request.getEvents());
            compilation.setEvents(events);
            log.info("Подборке присвоен новый список событий: events={}", request.getEvents());
        }
        if (request.getPinned() != null) {
            compilation.setIsPinned(request.getPinned());
            log.info("Подборке присвоено новое значение isPinned: {}", request.getPinned());
        }

        compilationRepository.save(compilation);

        CompilationDto dto = mapper.map(compilation, CompilationDto.class);
        List<Event> events = compilation.getEvents();
        if (events != null && !events.isEmpty()) {
            List<EventShortDto> eventsDtos = mapToShortEvents(events);
            dto.setEvents(eventsDtos);
        }

        return dto;
    }

    @Override
    public void deleteCompilationById(int id) {
        Compilation compilation = compilationRepository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("Compilation with id=%d was not found", id))
        );

        compilationRepository.delete(compilation);
        log.info("Удалена подборка с id={}", id);
    }

    private List<EventShortDto> mapToShortEvents(List<Event> events) {
        Map<String, EventShortDto> urisMap = events.stream()
                .map(event -> mapper.map(event, EventShortDto.class))
                .collect(Collectors.toMap(dto -> UriComponentsBuilder.fromUriString("/events")
                        .pathSegment(String.valueOf(dto.getId()))
                        .toUriString(), Function.identity()));

        ArrayList<EventShortDto> dtos = new ArrayList<>();
        for (Map.Entry<String, EventShortDto> entry : urisMap.entrySet()) {
            EventShortDto value = entry.getValue();

            List<ReturnStatsDto> stats = client.getStats(LocalDateTime.now().minusMonths(1), LocalDateTime.now(), List.of(entry.getKey()), true);
            if (!stats.isEmpty()) {
                Integer views = stats.getFirst().getHits();
                value.setViews(views);
            }
            dtos.add(value);
        }

        return dtos;
    }
}