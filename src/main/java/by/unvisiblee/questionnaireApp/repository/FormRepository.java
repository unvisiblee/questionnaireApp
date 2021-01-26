package by.unvisiblee.questionnaireApp.repository;

import by.unvisiblee.questionnaireApp.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
    Optional<Form> findById(Long id);
    Optional<Form> findByUserId(Long id);
}
