package by.unvisiblee.questionnaireApp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "form", schema = "public")
public class Form extends BaseEntity {

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List <Field> fields;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)

    private User user;

    public Form() {
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
