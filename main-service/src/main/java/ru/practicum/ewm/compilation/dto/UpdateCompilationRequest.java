package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class UpdateCompilationRequest {
    Set<Integer> events;
    Boolean pinned;
    @Size(min = 1, max = 50)
    String title;
}