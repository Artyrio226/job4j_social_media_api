package ru.job4j.socialmediaapi.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.job4j.socialmediaapi.dto.UserDto;
import ru.job4j.socialmediaapi.model.User;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserDto userToUserDto(User user);
}
