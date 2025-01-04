package ru.practicum.ewm.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.practicum.ewm.request.dto.enums.Status;

import java.time.LocalDateTime;

@Data
public class ParticipationRequestDto {
    Integer id;
    @JsonProperty("event")
    Integer eventId;
    @JsonProperty("requester")
    Integer userId;
    Status status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;
}