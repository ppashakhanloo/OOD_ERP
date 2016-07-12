package project;

import database.ModuleDAO;
import database.SystemDAO;
import utility.Identifiable;

import java.util.ArrayList;

public class System extends Identifiable {

    private String name;

    public System(String id, String name) {
        this.setID(id);
        this.name = name;
    }

    public System(String name) {
        this.setID(generateNDigitID(ID_LENGTH));
        this.name = name;
    }

    public static void main(String[] args) {

    }

    public ArrayList<Module> getModules() {
        SystemDAO systemDAO = SystemDAO.getInstance();
        return systemDAO.getModules(getID());
    }

    public boolean addModule(Module module) {
        return ModuleDAO.getInstance().add(module, getID());
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
//        return "ID=" + getID() + ", name=" + name;
        return name + " (" + getID() + ")";
    }
}
