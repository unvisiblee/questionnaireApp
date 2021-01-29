package by.unvisiblee.questionnaireApp.mapper;

import by.unvisiblee.questionnaireApp.dto.FieldRequestDto;
import by.unvisiblee.questionnaireApp.dto.FieldResponseDto;
import by.unvisiblee.questionnaireApp.exception.EntityNotFoundException;
import by.unvisiblee.questionnaireApp.model.Field;
import by.unvisiblee.questionnaireApp.model.Form;
import by.unvisiblee.questionnaireApp.repository.FormRepository;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class FieldMapper {

    private FormRepository formRepository;

    @Mapping(target = "formId", expression = "java(field.getForm().getId())" )
    public abstract FieldResponseDto fieldToFieldDto(Field field);

    @InheritInverseConfiguration
    @Mapping(target = "form", source = "form")
    public abstract Field fieldDtoToField(FieldRequestDto fieldRequestDto, Form form);

    @Mapping(target = "id", expression = "java(fieldResponseDto.getId())")
    public abstract Field fieldResponseDtoToField1(FieldResponseDto fieldResponseDto, Form form);

    Field fieldResponseDtoToField(FieldResponseDto fieldResponseDto) {
        Form form = formRepository
                .findById(fieldResponseDto.getFormId())
                .orElseThrow(() -> new EntityNotFoundException(Form.class, fieldResponseDto.getFormId().toString()));
        return fieldResponseDtoToField1(fieldResponseDto, form);
    }

    @Autowired
    public void setFormRepository(FormRepository formRepository) {
        this.formRepository = formRepository;
    }
}
