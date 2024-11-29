package ru.practicum.mainserver.service;

import com.example.maincommon.dto.user.NewUserRequest;
import com.example.maincommon.dto.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll(int from, int size);

    UserDto create(NewUserRequest newUser);

    void delete(Long userId);
}
