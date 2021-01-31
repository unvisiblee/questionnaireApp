package by.unvisiblee.questionnaireApp.exception;

public class EntityNotFoundException extends RuntimeException {
    private Class<?> entityName;
    private String entityId;

    public EntityNotFoundException(Class<?> entityName, String entityId) {
        this.entityName = entityName;
        this.entityId = entityId;
    }


    public Class<?> getEntityName() {
        return entityName;
    }

    public void setEntityName(Class<?> entityName) {
        this.entityName = entityName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
