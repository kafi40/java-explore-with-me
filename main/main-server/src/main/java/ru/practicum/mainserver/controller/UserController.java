package ru.practicum.mainserver.controller;

import com.example.maincommon.dto.participation.ParticipationRequestDto;
import com.example.maincommon.dto.user.NewUserRequest;
import com.example.maincommon.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainserver.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getParticipation(@PathVariable Long userId) {
        log.info("Server main (controller): Get participation for user {}", userId);
        return null;
    }

    @PostMapping("/users/{userId}/requests")
    public ParticipationRequestDto createParticipation(
            @PathVariable Long userId,
            @RequestBody  ParticipationRequestDto newParticipation
    ) {
        log.info("Server main (controller): Create participation for user {}", userId);
        return null;
    }

    @PatchMapping("/users/{userId}/requests/{requestsId/cancel")
    public ParticipationRequestDto cancelParticipation(
            @PathVariable Long userId
    ) {
        log.info("Server main (controller): Cancel participation for user {}", userId);
        return null;
    }

    @GetMapping("/admin/users")
    public List<UserDto> getAll(
            @RequestParam(required = false) List<Integer> ids,
            @RequestParam(required = false, defaultValue = "0") int from,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        log.info("Server main (controller): Get user with param ids={}, from={}, size={}", ids, from, size);
        return null;
    }

    @PostMapping("/admin/users")
    public UserDto create(@RequestBody NewUserRequest newUser) {
        log.info("Server main (controller): Create user {}", newUser);
        return null;
    }

    @DeleteMapping("/admin/users/{userId}")
    public void delete(@PathVariable Long userId) {
        log.info("Server main (controller): Delete user {}", userId);
    }
}
