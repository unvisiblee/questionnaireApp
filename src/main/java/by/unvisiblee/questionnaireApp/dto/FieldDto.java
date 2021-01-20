package by.unvisiblee.questionnaireApp.dto;

import by.unvisiblee.questionnaireApp.model.FieldType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

public class FieldDto {
    @NotBlank
    private String label;
    @NotBlank
    private FieldType fieldType;
    @NotBlank
    private boolean required;
    @NotBlank
    private boolean active;

    public FieldDto() {
    }

    public FieldDto(String label, FieldType fieldType, boolean required, boolean active) {
        this.label = label;
        this.fieldType = fieldType;
        this.required = required;
        this.active = active;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
