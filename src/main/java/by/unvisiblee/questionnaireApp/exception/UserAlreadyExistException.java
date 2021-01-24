package by.unvisiblee.questionnaireApp.exception;

public class UserAlreadyExistException extends RuntimeException {
    private String field; // email or username
    private String value;


    public UserAlreadyExistException(String value, String fieldValue) {
        this.field = fieldValue;
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
