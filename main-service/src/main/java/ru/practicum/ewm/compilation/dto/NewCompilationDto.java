package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class NewCompilationDto {
    List<Integer> events;
    Boolean pinned;
    @NotBlank
    @NotNull
    @Size(min = 1, max = 50, message = "Имя подборки должно содержать минимум 1 максимум 50 символов")
    String title;
}