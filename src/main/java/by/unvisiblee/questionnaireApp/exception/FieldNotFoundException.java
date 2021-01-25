package by.unvisiblee.questionnaireApp.exception;

public class FieldNotFoundException extends RuntimeException{
    public FieldNotFoundException(String id) {
        super(id);
    }
}
