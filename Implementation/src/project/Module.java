package project;

import database.ModuleDAO;
import database.ModuleModificationDAO;
import resource.HumanResource;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Module {

    int ID_LENGTH = 6;
    private String ID;
    private String name;
    private Date developmentStart;
    private Date developmentEnd;

    public Module() {
        setID(generateNDigitID(ID_LENGTH));
    }

    public Module(String iD, String name, Date developmentStart,
                  Date developmentEnd) {
        ID = iD;
        this.name = name;
        this.developmentStart = developmentStart;
        this.developmentEnd = developmentEnd;
    }

    private String generateNDigitID(int n) {
        Random random = new Random();
        return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
                * 9 * Math.pow(10, n - 1)));
    }

    public ArrayList<ModuleModification> getModuleModifications() {
        return ModuleModificationDAO.getInstance().getByModuleID(getID());
    }

    public boolean addModification(ModuleModification mod) {
        return ModuleModificationDAO.getInstance().add(mod, getID());
    }

    public ArrayList<HumanResource> getDevelopers() {
        return ModuleDAO.getInstance().getDevelopers(getID());
    }

    public boolean addDeveloper(HumanResource developer) {
        return ModuleDAO.getInstance().addDeveloper(getID(), developer);
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
        ModuleDAO modDAO = ModuleDAO.getInstance();
        return modDAO.update(new Module(getID(), name, getDevelopmentStart(),
                getDevelopmentEnd()));
    }

    public Date getDevelopmentStart() {
        return developmentStart;
    }

    public boolean setDevelopmentStart(Date developmentStart) {
        this.developmentStart = developmentStart;
        ModuleDAO modDAO = ModuleDAO.getInstance();
        return modDAO.update(new Module(getID(), getName(), developmentStart,
                getDevelopmentEnd()));
    }

    public Date getDevelopmentEnd() {
        return developmentEnd;
    }

    public boolean setDevelopmentEnd(Date developmentEnd) {
        this.developmentEnd = developmentEnd;
        ModuleDAO modDAO = ModuleDAO.getInstance();
        return modDAO.update(new Module(getID(), getName(),
                getDevelopmentStart(), developmentEnd));
    }

    @Override
    public String toString() {
        return "ID=" + ID + ", name-" + name + ", developmentStart=" + developmentStart + ", developmentEnd=" + developmentEnd;
    }
}
