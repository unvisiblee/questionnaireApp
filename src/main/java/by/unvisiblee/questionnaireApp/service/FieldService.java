package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.dto.FieldRequest;
import by.unvisiblee.questionnaireApp.dto.FieldResponse;
import by.unvisiblee.questionnaireApp.exception.FieldNotFoundException;
import by.unvisiblee.questionnaireApp.exception.FormNotFoundException;
import by.unvisiblee.questionnaireApp.mapper.FieldMapper;
import by.unvisiblee.questionnaireApp.model.Field;
import by.unvisiblee.questionnaireApp.model.Form;
import by.unvisiblee.questionnaireApp.repository.FieldRepository;
import by.unvisiblee.questionnaireApp.repository.FormRepository;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.expression.Fields;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService {
    private final FieldMapper fieldMapper;
    private final FormRepository formRepository;
    private final FieldRepository fieldRepository;

    public FieldService(FieldMapper fieldMapper, FormRepository formRepository, FieldRepository fieldRepository) {
        this.fieldMapper = fieldMapper;
        this.formRepository = formRepository;
        this.fieldRepository = fieldRepository;
    }

    @Transactional
    public void create(FieldRequest fieldRequest) {
        Form form = formRepository.findById(fieldRequest.getFormId())
                    .orElseThrow(() -> new FormNotFoundException(fieldRequest.getFormId().toString()));

        Field field = fieldMapper.fieldDtoToField(fieldRequest, form);
        fieldRepository.save(field);
    }

    @Transactional(readOnly = true)
    public FieldResponse getField(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new FieldNotFoundException(id.toString()));
        return fieldMapper.fieldToFieldDto(field);
    }

    @Transactional(readOnly = true)
    public List<FieldResponse> getFieldsByForm(Long form_id) {
        Form form = formRepository.findById(form_id).orElseThrow(() -> new FormNotFoundException(form_id.toString()));
        return fieldRepository.findAllByForm(form)
                .stream()
                .map(fieldMapper::fieldToFieldDto)
                .collect(Collectors.toList());
    }

}
