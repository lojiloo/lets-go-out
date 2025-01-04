package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.error.exceptions.DataIntegrityViolationException;
import ru.practicum.ewm.error.exceptions.NotFoundException;
import ru.practicum.ewm.user.converter.UserConverter;
import ru.practicum.ewm.user.dto.NewUserDto;
import ru.practicum.ewm.user.dto.UserDto;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UsersRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserConverter converter;
    private final UsersRepository repository;

    @Override
    public UserDto addNewUser(NewUserDto request) {
        if (repository.findByEmailIgnoreCase(request.getEmail().toUpperCase()) != null) {
            throw new DataIntegrityViolationException(String.format("Данный email уже занят другим пользователем: %s", request.getEmail()));
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        repository.save(user);

        return converter.toUserDto(user);
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        if (ids == null || ids.isEmpty()) {
            return repository.findAll(PageRequest.of(from, size))
                    .stream()
                    .map(converter::toUserDto)
                    .toList();
        }

        return repository.findAllByIdIn(ids, PageRequest.of(from, size))
                .stream()
                .map(converter::toUserDto)
                .toList();
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = repository.findById(id).orElseThrow(
                () -> new NotFoundException(String.format("User with id=%d was not found", id))
        );

        repository.delete(user);
    }
}