package ru.practicum.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReturnStatsDto {
    String app;
    String uri;
    Long hits;
}
