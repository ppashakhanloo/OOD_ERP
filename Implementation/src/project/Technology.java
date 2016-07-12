package project;

import database.ProjectDAO;
import utility.Identifiable;

public class Technology extends Identifiable {
    private String name;
    private String reason;

    public Technology(String name, String reason) {
        super();
        this.name = name;
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
//        return "name=" + name + ", reason=" + reason;
        return name + "->" + reason;
    }
}
