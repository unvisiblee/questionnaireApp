package by.unvisiblee.questionnaireApp.exception;

public class QuestionnaireServiceException extends RuntimeException {
    public QuestionnaireServiceException(String message, Exception exception) {
        super(message, exception);
    }

    public QuestionnaireServiceException(String message) {
        super(message);
    }
}
