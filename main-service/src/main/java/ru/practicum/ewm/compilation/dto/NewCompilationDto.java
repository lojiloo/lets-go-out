package ru.practicum.ewm.compilation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCompilationDto {
    List<Integer> events;
    Boolean pinned;
    @NotBlank
    @NotNull
    @Size(min = 1, max = 50, message = "Имя подборки должно содержать минимум 1 максимум 50 символов")
    String title;
}