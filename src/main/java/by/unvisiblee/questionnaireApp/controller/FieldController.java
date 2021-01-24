package by.unvisiblee.questionnaireApp.controller;

import by.unvisiblee.questionnaireApp.dto.FieldDto;
import by.unvisiblee.questionnaireApp.service.FieldService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/field")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping
    public void createField(@RequestBody @Valid FieldDto fieldDto) {
        fieldService.create(fieldDto);
    }
}
