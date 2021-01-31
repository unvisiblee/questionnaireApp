package by.unvisiblee.questionnaireApp.mapper;

import by.unvisiblee.questionnaireApp.dto.ResponseDto;
import by.unvisiblee.questionnaireApp.exception.EntityNotFoundException;
import by.unvisiblee.questionnaireApp.model.Form;
import by.unvisiblee.questionnaireApp.model.Response;
import by.unvisiblee.questionnaireApp.repository.FormRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = ResponseForFieldMapper.class)
public abstract class ResponseMapper {
    private FormRepository formRepository;


    @Mapping(target = "formId", expression = "java(response.getForm().getId())")
    public abstract ResponseDto responseToResponseDto(Response response);

    @Mapping(target = "form", expression = "java(getFormById(responseDto.getFormId()))")
    @Mapping(target = "responseForFields", ignore = true)
    public abstract Response responseDtoToResponse(ResponseDto responseDto);


    protected Form getFormById(Long id) {
        return formRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, id.toString()));
    }

    @Autowired
    public void setFormRepository(FormRepository formRepository) {
        this.formRepository = formRepository;
    }
}
