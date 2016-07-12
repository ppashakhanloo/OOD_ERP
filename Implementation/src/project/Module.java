package project;

import database.ModuleDAO;
import database.ModuleModificationDAO;
import resource.HumanResource;
import utility.Identifiable;

import java.util.ArrayList;
import java.util.Date;

public class Module extends Identifiable {

    private String name;
    private Date developmentStart;
    private Date developmentEnd;

    public Module() {
        setID(generateNDigitID(ID_LENGTH));
    }

    public Module(String iD, String name, Date developmentStart,
                  Date developmentEnd) {
        setID(iD);
        this.name = name;
        this.developmentStart = developmentStart;
        this.developmentEnd = developmentEnd;
    }

    public ArrayList<ModuleModification> getModuleModifications() {
        return ModuleModificationDAO.getInstance().getByModuleID(getID());
    }

    public boolean addModification(ModuleModification mod) {
        return ModuleModificationDAO.getInstance().add(mod, getID());
    }

    public boolean addDeveloper(HumanResource developer) {
        return ModuleDAO.getInstance().addDeveloper(getID(), developer);
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

    public Date getDevelopmentEnd() {
        return developmentEnd;
    }

    @Override
    public String toString() {
//        return "ID=" + getID() + ", name=" + name + ", developmentStart=" + developmentStart + ", developmentEnd=" + developmentEnd;
        return name + "(" + getID() + ")";
    }
}
