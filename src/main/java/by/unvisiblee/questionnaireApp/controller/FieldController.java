package by.unvisiblee.questionnaireApp.controller;

import by.unvisiblee.questionnaireApp.dto.FieldDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/field")
public class FieldController {

    @PostMapping
    public void createField(@RequestBody FieldDto fieldDto) {

    }
}
