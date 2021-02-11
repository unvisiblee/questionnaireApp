package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.dto.FieldRequestDto;
import by.unvisiblee.questionnaireApp.dto.FieldResponseDto;
import by.unvisiblee.questionnaireApp.exception.EntityNotFoundException;
import by.unvisiblee.questionnaireApp.mapper.FieldMapper;
import by.unvisiblee.questionnaireApp.model.Field;
import by.unvisiblee.questionnaireApp.model.Form;
import by.unvisiblee.questionnaireApp.repository.FieldRepository;
import by.unvisiblee.questionnaireApp.repository.FormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService {
    private final Logger logger = LoggerFactory.getLogger(FieldService.class);
    private final FieldMapper fieldMapper;
    private final FormRepository formRepository;
    private final FieldRepository fieldRepository;

    public FieldService(FieldMapper fieldMapper, FormRepository formRepository, FieldRepository fieldRepository) {
        this.fieldMapper = fieldMapper;
        this.formRepository = formRepository;
        this.fieldRepository = fieldRepository;
    }

    @Transactional
    public void create(FieldRequestDto fieldRequestDto) {
        Form form = formRepository.findById(fieldRequestDto.getFormId())
                    .orElseThrow(() -> new EntityNotFoundException(Form.class, fieldRequestDto.getFormId().toString()));

        Field field = fieldMapper.fieldDtoToField(fieldRequestDto, form);
        fieldRepository.save(field);
        logger.info("Created field: " + field.getLabel() + ", " + field.getId());
    }

    @Transactional(readOnly = true)
    public FieldResponseDto getField(Long id) {
        Field field = fieldRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Field.class, id.toString()));
        return fieldMapper.fieldToFieldDto(field);
    }

    @Transactional(readOnly = true)
    public List<FieldResponseDto> getFieldsByForm(Long form_id) {
        Form form = formRepository.findById(form_id).orElseThrow(() -> new EntityNotFoundException(Form.class, form_id.toString()));
        return fieldRepository.findAllByForm(form)
                .stream()
                .map(fieldMapper::fieldToFieldDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public FieldResponseDto update(Long id, FieldRequestDto fieldRequestDto) {
        Field fieldFromDb = getFieldById(id);
        Form form = getFormById(fieldRequestDto.getFormId());

        fieldFromDb.setFieldType(fieldRequestDto.getFieldType());
        fieldFromDb.setOptions(fieldRequestDto.getOptions());
        fieldFromDb.setActive(fieldRequestDto.getActive());
        fieldFromDb.setForm(form);
        fieldFromDb.setLabel(fieldRequestDto.getLabel());
        fieldFromDb.setRequired(fieldRequestDto.getRequired());

        fieldRepository.save(fieldFromDb);

        return fieldMapper.fieldToFieldDto(fieldFromDb);
    }

    private Field getFieldById(Long fieldId) {
        return fieldRepository
                .findById(fieldId)
                .orElseThrow(() -> new EntityNotFoundException(Field.class, fieldId.toString()));
    }

    private Form getFormById(Long formId) {
        return formRepository
                .findById(formId)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, formId.toString()));
    }

    public void delete(Long fieldId) {
        fieldRepository.deleteById(fieldId);
    }
}
