package tech.shmy.portal.application.domain;


public enum Permission {
    POST$LIST,
    POST$DETAIL;
    private String[] parseName() {
        return name().toLowerCase().split("\\$");
    }
    public String getObject() {
        return parseName()[0];
    }
    public String getAction() {
        return parseName()[1];
    }
    public String getObjectDescript() {
        return "permission." + parseName()[0];
    }
    public String getActionDescript() {
        return "permission." + parseName()[0] + "." + parseName()[1];
    }
}
