package project;

import database.ModuleDAO;
import database.SystemDAO;
import utility.Identifiable;

import java.util.ArrayList;
import java.util.Random;

public class System extends Identifiable {
    private String ID;
    private String name;

    public System() {
        this.setID(generateNDigitID(ID_LENGTH));
    }

    public System(String id, String name) {
        this.setID(id);
        this.name = name;
    }

    public System(String name) {
        this.setID(generateNDigitID(ID_LENGTH));
        this.name = name;
    }

    public void removeModule(Module module) {
        ModuleDAO moduleDAO = ModuleDAO.getInstance();
        moduleDAO.remove(module.getID());
    }

    public int getModulesCount() {
        SystemDAO systemDAO = SystemDAO.getInstance();
        return systemDAO.getModules(getID()).size();
    }

    public ArrayList<Module> getModules() {
        SystemDAO systemDAO = SystemDAO.getInstance();
        return systemDAO.getModules(getID());
    }

    public Module getModule(String id) {
        return ModuleDAO.getInstance().get(id);
    }

    public boolean addModule(Module module) {
        return ModuleDAO.getInstance().add(module, getID());
    }

    public String getID() {
        return ID;
    }

    private void setID(String iD) {
        ID = iD;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        this.name = name;
        SystemDAO sysDAO = SystemDAO.getInstance();
        return sysDAO.update(new System(getID(), name));
    }

    @Override
    public String toString() {
        return "ID=" + ID + ", name=" + name;
    }

    public static void main(String[] args) {

    }
}
