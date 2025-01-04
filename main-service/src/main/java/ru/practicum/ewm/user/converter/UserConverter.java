package ru.practicum.ewm.user.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.model.User;

@Component
public class UserConverter {
    private final ModelMapper mapper;

    public UserConverter() {
        this.mapper = new ModelMapper();
    }

    public UserDto toUserDto(User user) {
        return mapper.map(user, UserDto.class);
    }

    public UserShortDto toUserShortDto(User user) {
        return mapper.map(user, UserShortDto.class);
    }

    public User fromNewtoUser(NewUserDto dto, int id) {
        User user = mapper.map(dto, User.class);
        user.setId(id);

        return user;
    }
}