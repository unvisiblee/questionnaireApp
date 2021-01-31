package by.unvisiblee.questionnaireApp.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class ResponseDto {
    private Long id;
    @NotNull
    private Long formId;
    private List<ResponseForFieldDto> responseForFields;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public List<ResponseForFieldDto> getResponseForFields() {
        return responseForFields;
    }

    public void setResponseForFields(List<ResponseForFieldDto> responseForFields) {
        this.responseForFields = responseForFields;
    }
}
