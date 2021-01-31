package by.unvisiblee.questionnaireApp.dto;

import by.unvisiblee.questionnaireApp.model.Field;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class FormDto {
    private Long id;
    private List<FieldResponseDto> fields;
    @NotBlank
    private Long userId;


    public List<FieldResponseDto> getFields() {
        return fields;
    }

    public void setFields(List<FieldResponseDto> fields) {
        this.fields = fields;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
