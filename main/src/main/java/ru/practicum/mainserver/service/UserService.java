package ru.practicum.mainserver.service;

import ru.practicum.mainserver.dto.user.NewUserRequest;
import ru.practicum.mainserver.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll(List<Long> userIds, int from, int size);

    UserDto create(NewUserRequest newUser);

    void delete(Long userId);
}
