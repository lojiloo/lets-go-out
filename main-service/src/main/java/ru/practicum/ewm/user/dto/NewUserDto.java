package ru.practicum.ewm.user.dto;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewUserDto {
    @NotEmpty
    @Email
    @Size(min = 6, max = 254)
    String email;
    @NotBlank
    @NotNull
    @Size(min = 2, max = 250)
    String name;
}