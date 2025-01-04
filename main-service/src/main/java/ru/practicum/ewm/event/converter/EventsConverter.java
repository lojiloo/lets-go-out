package ru.practicum.ewm.event.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;

@Component
public class EventsConverter {
    private final ModelMapper mapper;

    public EventsConverter() {
        this.mapper = new ModelMapper();
    }

    public EventFullDto toFullDto(Event event) {
        return mapper.map(event, EventFullDto.class);
    }

    public EventShortDto toShortDto(Event event) {
        return mapper.map(event, EventShortDto.class);
    }
}
