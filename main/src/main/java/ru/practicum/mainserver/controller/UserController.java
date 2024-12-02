package ru.practicum.mainserver.controller;

import ru.practicum.mainserver.dto.user.NewUserRequest;
import ru.practicum.mainserver.dto.user.UserDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/admin/users")
    public List<UserDto> getAll(
            @RequestParam(required = false) List<Long> ids,
            @PositiveOrZero @RequestParam(required = false, defaultValue = "0") int from,
            @Positive @RequestParam(required = false, defaultValue = "10") int size
    ) {
        log.info("Server main (UserController): Get user with param ids={}, from={}, size={}", ids, from, size);
        return userService.getAll(ids, from, size);
    }

    @PostMapping("/admin/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody NewUserRequest newUser) {
        log.info("Server main (UserController): Create user {}", newUser);
        return userService.create(newUser);
    }

    @DeleteMapping("/admin/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long userId) {
        log.info("Server main (UserController): Delete user {}", userId);
        userService.delete(userId);
    }
}
