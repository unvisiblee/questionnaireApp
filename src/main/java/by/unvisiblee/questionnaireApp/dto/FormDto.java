package by.unvisiblee.questionnaireApp.dto;

import by.unvisiblee.questionnaireApp.model.Field;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class FormDto {

    private List<Field> fieldList;
    @NotBlank
    private String username;


    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
