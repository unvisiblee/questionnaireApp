package by.unvisiblee.questionnaireApp.repository;

import by.unvisiblee.questionnaireApp.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    List<Response> findAllByFormId(Long id);
}
