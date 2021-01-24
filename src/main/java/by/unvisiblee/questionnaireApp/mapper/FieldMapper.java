package by.unvisiblee.questionnaireApp.mapper;

import by.unvisiblee.questionnaireApp.dto.FieldDto;
import by.unvisiblee.questionnaireApp.model.Field;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FieldMapper {

    FieldMapper INSTANCE = Mappers.getMapper( FieldMapper.class );

    FieldDto fieldToFieldDto(Field field);

    @InheritInverseConfiguration
    Field fieldDtoToField(FieldDto fieldDto);
}
