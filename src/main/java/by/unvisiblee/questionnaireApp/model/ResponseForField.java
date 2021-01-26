package by.unvisiblee.questionnaireApp.model;

import javax.persistence.*;

@Entity
@Table(name = "response_for_field")
public class ResponseForField extends BaseEntity {

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "field_id", referencedColumnName = "id")
    private Field field;

    @ManyToOne
    @JoinColumn(name = "response_id", referencedColumnName = "id")
    private Response response;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
