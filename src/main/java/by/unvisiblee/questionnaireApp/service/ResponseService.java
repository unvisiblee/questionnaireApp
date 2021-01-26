package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.dto.ResponseDto;
import by.unvisiblee.questionnaireApp.mapper.ResponseForFieldMapper;
import by.unvisiblee.questionnaireApp.mapper.ResponseMapper;
import by.unvisiblee.questionnaireApp.model.Response;
import by.unvisiblee.questionnaireApp.model.ResponseForField;
import by.unvisiblee.questionnaireApp.repository.ResponseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    private final ResponseRepository responseRepository;
    private final ResponseMapper responseMapper;
    private final ResponseForFieldMapper responseForFieldMapper;


    public ResponseService(ResponseRepository responseRepository, ResponseMapper responseMapper, ResponseForFieldMapper responseForFieldMapper) {
        this.responseRepository = responseRepository;
        this.responseMapper = responseMapper;
        this.responseForFieldMapper = responseForFieldMapper;
    }

    public void createResponse(ResponseDto responseDto) {
        Response response = responseMapper.responseDtoToResponse(responseDto);

        response.setResponseForFields(responseDto
                .getResponseForFields()
                .stream()
                .map(
                        (responseForFieldDto -> responseForFieldMapper.responseForFieldDtoToResponseForField(responseForFieldDto, response))
                )
                .collect(Collectors.toList()));
        responseRepository.save(response);
    }


    public List<Response> getResponsesByFormId(Long formId) {
        return responseRepository.findAllByFormId(formId);
    }
}
