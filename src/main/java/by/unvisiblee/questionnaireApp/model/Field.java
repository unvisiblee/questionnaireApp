package by.unvisiblee.questionnaireApp.model;

import javax.persistence.*;

@Entity
@Table(name = "field", schema = "public")
public class Field extends BaseEntity{

    @Column(name = "label", unique = true, nullable = false)
    private String label;

    @Column(name = "field_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Column(name = "required")
    private boolean required;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name="form_id", referencedColumnName="id")
    private Form form;

    public Field() {
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
