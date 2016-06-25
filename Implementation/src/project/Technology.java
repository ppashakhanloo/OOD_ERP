package project;

import database.ProjectDAO;

public class Technology {
    String name;
    String reason;

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
