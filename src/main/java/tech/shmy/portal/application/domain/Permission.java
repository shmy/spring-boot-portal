package tech.shmy.portal.application.domain;


public enum Permission {
    POST$VIEW,
    POST$CREATE,
    POST$UPDATE,
    POST$DELETE,
    USER$VIEW,
    USER$CREATE,
    USER$UPDATE,
    USER$DELETE
    ;
    private String[] parseName() {
        return name().toLowerCase().split("\\$");
    }
    public String getObject() {
        return parseName()[0];
    }
    public String getAction() {
        return parseName()[1];
    }
    public String getObjectName() {
        return "permission." + parseName()[0];
    }
    public String getObjectDescription() {
        return getObjectName() + ".description";
    }
    public String getActionName() {
        return "permission." + parseName()[0] + "." + parseName()[1];
    }
    public String getActionDescription() {
        return getActionName() + ".description";
    }
}
