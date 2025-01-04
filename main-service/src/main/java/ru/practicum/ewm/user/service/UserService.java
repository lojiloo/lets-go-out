package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto addNewUser(NewUserDto request);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUserById(Integer id);

}