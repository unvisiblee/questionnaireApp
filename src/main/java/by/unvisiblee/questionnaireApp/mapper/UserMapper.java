package by.unvisiblee.questionnaireApp.mapper;

import by.unvisiblee.questionnaireApp.dto.UserDto;
import by.unvisiblee.questionnaireApp.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToUserDto(User user);
}
