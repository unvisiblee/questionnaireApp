package by.unvisiblee.questionnaireApp.controller;

import by.unvisiblee.questionnaireApp.dto.FieldRequest;
import by.unvisiblee.questionnaireApp.dto.FieldResponse;
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
    public void createField(@RequestBody @Valid FieldRequest fieldRequest) {
        fieldService.create(fieldRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldResponse> getFieldById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getField(id));
    }

    @GetMapping("/by-form/{form_id}")
    public ResponseEntity<List<FieldResponse>> getFieldsByForm(@PathVariable Long form_id) {
        return ResponseEntity.status(HttpStatus.OK).body(fieldService.getFieldsByForm(form_id));
    }
}
