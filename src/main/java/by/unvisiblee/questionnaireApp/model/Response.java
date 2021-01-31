package by.unvisiblee.questionnaireApp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "response")
public class Response extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "form_id", referencedColumnName = "id")
    private Form form;

    @OneToMany(mappedBy = "response", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResponseForField> responseForFields;


    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public List<ResponseForField> getResponseForFields() {
        return responseForFields;
    }

    public void setResponseForFields(List<ResponseForField> responseForFields) {
        this.responseForFields = responseForFields;
    }
}
