package by.unvisiblee.questionnaireApp.repository;

import by.unvisiblee.questionnaireApp.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Long> {
    Optional<Form> findById(Long id);
}
