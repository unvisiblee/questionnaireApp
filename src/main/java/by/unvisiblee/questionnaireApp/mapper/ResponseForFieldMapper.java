package by.unvisiblee.questionnaireApp.mapper;

import by.unvisiblee.questionnaireApp.dto.ResponseForFieldDto;
import by.unvisiblee.questionnaireApp.exception.EntityNotFoundException;
import by.unvisiblee.questionnaireApp.model.Field;
import by.unvisiblee.questionnaireApp.model.Response;
import by.unvisiblee.questionnaireApp.model.ResponseForField;
import by.unvisiblee.questionnaireApp.repository.FieldRepository;
import by.unvisiblee.questionnaireApp.repository.ResponseRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ResponseForFieldMapper {
    private FieldRepository fieldRepository;
    private ResponseRepository responseRepository;

    @Mapping(target = "responseId", expression = "java(responseForField.getResponse().getId())")
    @Mapping(target = "fieldId", expression = "java(responseForField.getField().getId())")
    public abstract ResponseForFieldDto responseForFieldToResponseForFieldDto(ResponseForField responseForField);

    @Mapping(target = "field", expression = "java(getFieldById(responseForFieldDto.getFieldId()))")
    @Mapping(target = "response", expression = "java(getResponseById(responseForFieldDto.getResponseId()))")
    public abstract ResponseForField responseForFieldDtoToResponseForField(ResponseForFieldDto responseForFieldDto);

    @Mapping(target = "field", expression = "java(getFieldById(responseForFieldDto.getFieldId()))")
    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "response", expression = "java(getResponseById(responseForFieldDto.getResponseId()))")
    public abstract ResponseForField responseForFieldDtoToResponseForField(ResponseForFieldDto responseForFieldDto, Response response);


    protected Field getFieldById(Long id) {
        return fieldRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Field.class, id.toString()));
    }

    protected Response getResponseById(Long id) {
        return responseRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Response.class, id.toString()));
    }

    @Autowired
    public void setFieldRepository(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Autowired
    public void setResponseRepository(ResponseRepository responseRepository) {
        this.responseRepository = responseRepository;
    }
}
