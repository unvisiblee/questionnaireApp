package by.unvisiblee.questionnaireApp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Form {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Field> fields;

    @OneToOne
    private User user;
}
