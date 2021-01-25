package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.dto.FormDto;
import by.unvisiblee.questionnaireApp.model.Form;
import by.unvisiblee.questionnaireApp.model.User;
import by.unvisiblee.questionnaireApp.repository.FormRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormService {

    public final AuthService authService;
    public final FormRepository formRepository;

    public FormService(AuthService authService, FormRepository formRepository) {
        this.authService = authService;
        this.formRepository = formRepository;
    }

    @Transactional
    public void create(User user) {
        Form form = new Form();
        form.setUser(user);
        formRepository.save(form);
    }
}
