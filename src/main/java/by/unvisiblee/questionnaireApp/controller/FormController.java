package by.unvisiblee.questionnaireApp.controller;

import by.unvisiblee.questionnaireApp.dto.FormDto;
import by.unvisiblee.questionnaireApp.service.FormService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/form")
public class FormController {

    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    /**
     * This mapping is relevant until we have only 1 form per user, if app will be
     * modified in way that one user will have more than 1 form - this method will
     * become useless.
     */
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<FormDto> getFormByUserId(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(formService.findByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormDto> getFormById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(formService.findById(id));
    }


}
