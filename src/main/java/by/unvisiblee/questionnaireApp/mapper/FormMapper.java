package by.unvisiblee.questionnaireApp.mapper;

import by.unvisiblee.questionnaireApp.dto.FieldResponseDto;
import by.unvisiblee.questionnaireApp.dto.FormDto;
import by.unvisiblee.questionnaireApp.exception.FormNotFoundException;
import by.unvisiblee.questionnaireApp.model.Field;
import by.unvisiblee.questionnaireApp.model.Form;
import by.unvisiblee.questionnaireApp.model.User;
import by.unvisiblee.questionnaireApp.repository.FormRepository;
import by.unvisiblee.questionnaireApp.repository.UserRepository;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {FieldMapper.class})
public abstract class FormMapper {

    private UserRepository userRepository;

    @Mapping(target = "userId", expression = "java(form.getUser().getId())")
    public abstract FormDto formToFormDto(Form form);

    @Mapping(target = "user", expression = "java(getUserById(formDto.getUserId()))")
    public abstract Form formDtoToForm(FormDto formDto);

    protected User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(id.toString()));
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
