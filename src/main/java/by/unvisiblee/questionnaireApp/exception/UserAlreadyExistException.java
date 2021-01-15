package by.unvisiblee.questionnaireApp.exception;

public class UserAlreadyExistException extends RuntimeException {
    String field; // email or username
    String value;


    public UserAlreadyExistException(String value, String fieldValue) {
        super();
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
