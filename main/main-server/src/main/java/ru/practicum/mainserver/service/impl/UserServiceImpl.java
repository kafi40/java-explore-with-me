package ru.practicum.mainserver.service.impl;

import com.example.maincommon.dto.user.NewUserRequest;
import com.example.maincommon.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.repository.UserRepository;
import ru.practicum.mainserver.service.UserService;
import ru.practicum.mainserver.service.entity.User;
import ru.practicum.mainserver.service.mapper.UserMapper;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll(int from, int size) {
        log.info("Server main (UserService): Try getAll()");
        PageRequest page = PageRequest.of(from > 0 ? from / size : 0, size);
        return userRepository.findMany(page).stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto create(NewUserRequest newUser) {
        log.info("Server main (UserService): Try create()");
        User user = userMapper.toEntity(newUser);
        user = userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public void delete(Long userId) {
        log.info("Server main (UserService): Try to delete");
        userRepository.deleteById(userId);
    }
}
