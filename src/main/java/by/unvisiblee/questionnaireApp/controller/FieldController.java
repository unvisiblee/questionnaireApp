package by.unvisiblee.questionnaireApp.controller;

import by.unvisiblee.questionnaireApp.dto.FieldRequestDto;
import by.unvisiblee.questionnaireApp.dto.FieldResponseDto;
import by.unvisiblee.questionnaireApp.service.FieldService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/field")
public class FieldController {

    private final FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping
    public void createField(@RequestBody @Valid FieldRequestDto fieldRequestDto) {
        fieldService.create(fieldRequestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldResponseDto> getFieldById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getField(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FieldResponseDto> updateFieldById(@PathVariable Long id, @RequestBody FieldRequestDto fieldRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.update(id, fieldRequestDto));
    }


    @GetMapping("/by-form/{form_id}")
    public ResponseEntity<List<FieldResponseDto>> getFieldsByForm(@PathVariable Long form_id) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldsByForm(form_id));

    }
}
