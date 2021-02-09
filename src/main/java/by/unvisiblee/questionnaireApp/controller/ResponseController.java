package by.unvisiblee.questionnaireApp.controller;

import by.unvisiblee.questionnaireApp.dto.ResponseDto;
import by.unvisiblee.questionnaireApp.model.Response;
import by.unvisiblee.questionnaireApp.service.ResponseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/response")
public class ResponseController {
    private final ResponseService responseService;

    public ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping("/by-form/{formId}")
    public ResponseEntity<List<ResponseDto>> getResponsesByFormId(@PathVariable Long formId) {
        return ResponseEntity.status(HttpStatus.OK).body(responseService.getResponsesByFormId(formId));
    }

    @PostMapping
    public ResponseEntity<String> createResponse(@RequestBody @Valid ResponseDto responseDto) {
        responseService.createResponse(responseDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
