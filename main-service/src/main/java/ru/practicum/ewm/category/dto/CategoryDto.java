package ru.practicum.ewm.category.dto;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDto {
    Integer id;
    @Size(min = 1, max = 50, message = "Имя категории должно содержать минимум 1 максимум 50 символов")
    String name;
}