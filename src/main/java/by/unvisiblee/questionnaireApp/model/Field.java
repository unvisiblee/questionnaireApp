package by.unvisiblee.questionnaireApp.model;

import javax.persistence.*;

@Entity
public class Field {
    @Id
    @GeneratedValue
    private Long id;

    private String label;
    private FieldType fieldType;
    private boolean required;
    private boolean active;

    @ManyToOne
    @JoinColumn(name="form_id", referencedColumnName="id")
    private Form form;

    public Field() {
    }

    public Field(Long id, String label, FieldType fieldType, boolean required, boolean active, Form form) {
        this.id = id;
        this.label = label;
        this.fieldType = fieldType;
        this.required = required;
        this.active = active;
        this.form = form;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }
}
