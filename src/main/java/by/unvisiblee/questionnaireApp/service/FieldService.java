package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.dto.FieldDto;
import by.unvisiblee.questionnaireApp.exception.FormNotFoundException;
import by.unvisiblee.questionnaireApp.mapper.FieldMapper;
import by.unvisiblee.questionnaireApp.model.Field;
import by.unvisiblee.questionnaireApp.model.Form;
import by.unvisiblee.questionnaireApp.repository.FieldRepository;
import by.unvisiblee.questionnaireApp.repository.FormRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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
    public void create(FieldDto fieldDto) {
        Form form = formRepository.findById(fieldDto.getFormId())
                    .orElseThrow(() -> new FormNotFoundException(fieldDto.getFormId().toString()));

        Field field = fieldMapper.fieldDtoToField(fieldDto, form);
        fieldRepository.save(field);
        System.out.println("test");
    }
}
