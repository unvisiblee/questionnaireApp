package by.unvisiblee.questionnaireApp.mapper;

import by.unvisiblee.questionnaireApp.dto.FieldRequest;
import by.unvisiblee.questionnaireApp.dto.FieldResponse;
import by.unvisiblee.questionnaireApp.model.Field;
import by.unvisiblee.questionnaireApp.model.Form;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FieldMapper {

    FieldMapper INSTANCE = Mappers.getMapper( FieldMapper.class );

    @Mapping(target = "formId", expression = "java(field.getForm().getId())" )
    FieldResponse fieldToFieldDto(Field field);

    @InheritInverseConfiguration
    @Mapping(target = "form", source = "form")
    Field fieldDtoToField(FieldRequest fieldRequest, Form form);
}
