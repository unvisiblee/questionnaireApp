package by.unvisiblee.questionnaireApp.mapper;

import by.unvisiblee.questionnaireApp.dto.FormDto;
import by.unvisiblee.questionnaireApp.model.Form;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FormMapper {
    FormMapper INSTANCE = Mappers.getMapper(FormMapper.class);

    FormDto formToFormDto(Form form);

    Form formDtoToForm(FormDto formDto);
}
