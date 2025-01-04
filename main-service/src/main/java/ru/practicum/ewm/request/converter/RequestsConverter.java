package ru.practicum.ewm.request.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.request.dto.ParticipationRequestDto;
import ru.practicum.ewm.request.model.Request;

@Component
public class RequestsConverter {
    private final ModelMapper mapper;

    public RequestsConverter() {
        this.mapper = new ModelMapper();
    }

    public ParticipationRequestDto toParticipationRequestDto(Request request) {
        mapper.typeMap(Request.class, ParticipationRequestDto.class).addMappings(mapper -> {
            mapper.map(Request::getCreatedOn, ParticipationRequestDto::setCreated);
        });
        return mapper.map(request, ParticipationRequestDto.class);
    }
}
