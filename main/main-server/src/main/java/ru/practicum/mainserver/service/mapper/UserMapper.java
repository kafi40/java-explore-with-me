package ru.practicum.mainserver.service.mapper;

import com.example.maincommon.dto.user.NewUserRequest;
import com.example.maincommon.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.practicum.mainserver.service.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    User toEntity(NewUserRequest newUser);

    UserDto toDto(User user);
}
