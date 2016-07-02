package project;

import database.ProjectDAO;
import utility.Identifiable;

public class Technology extends Identifiable {
    private String name;
    private String reason;

    public String getName() {
        return name;
    }

    public String getReason() {
        return reason;
    }

    public boolean setReason(String reason) {
        this.reason = reason;
        ProjectDAO projectDAO = ProjectDAO.getInstance();
        return projectDAO.updateTechnology(new Technology(getName(), reason));
    }

    public Technology(String name, String reason) {
        super();
        this.name = name;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "name=" + name + ", reason=" + reason;
    }
}
