package by.unvisiblee.questionnaireApp.dto;

import by.unvisiblee.questionnaireApp.model.FieldType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FieldDto {
    @NotBlank
    private String label;
    @NotNull
    private FieldType fieldType;
    @NotNull
    private Boolean required;
    @NotNull
    private Boolean active;

    public FieldDto() {
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
