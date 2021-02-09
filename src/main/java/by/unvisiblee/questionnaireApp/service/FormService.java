package by.unvisiblee.questionnaireApp.service;

import by.unvisiblee.questionnaireApp.dto.FormDto;
import by.unvisiblee.questionnaireApp.exception.EntityNotFoundException;
import by.unvisiblee.questionnaireApp.mapper.FormMapper;
import by.unvisiblee.questionnaireApp.model.Form;
import by.unvisiblee.questionnaireApp.model.User;
import by.unvisiblee.questionnaireApp.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormService {

    private final AuthService authService;
    private final FormRepository formRepository;
    private final FormMapper formMapper;

    public FormService(@Lazy AuthService authService, FormRepository formRepository, FormMapper formMapper) {
        this.authService = authService;
        this.formRepository = formRepository;
        this.formMapper = formMapper;
    }

    @Transactional
    public void create(User user) {
        Form form = new Form();
        form.setUser(user);
        formRepository.save(form);
    }

    public FormDto findByUserId(Long userId) {
        Form form = formRepository
                .findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, userId.toString()));
        return formMapper.formToFormDto(form);
    }

    public FormDto findById(Long id) {
        Form form = formRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Form.class, id.toString()));
        return formMapper.formToFormDto(form);
    }

}
