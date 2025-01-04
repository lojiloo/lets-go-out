package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestService {

    List<ParticipationRequestDto> getAllUsersRequestsById(Integer userId);

    ParticipationRequestDto sendParticipationRequest(Integer userId, Integer eventId);

    ParticipationRequestDto cancelParticipationRequest(Integer userId, Integer requestId);

}