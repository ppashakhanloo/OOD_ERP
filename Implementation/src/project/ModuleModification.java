package project;

import database.ModuleModificationDAO;
import resource.HumanResource;
import utility.Identifiable;

import java.util.ArrayList;
import java.util.Date;

public class ModuleModification extends Identifiable {
    private String modificationType;
    private Date modificationStart;
    private Date modificationEnd;

    public ModuleModification() {
        setID(generateNDigitID(ID_LENGTH));
    }

    public ModuleModification(String iD, String modificationType,
                              Date modificationStart, Date modificationEnd) {
        super();
        setID(iD);
        this.modificationType = modificationType;
        this.modificationStart = modificationStart;
        this.modificationEnd = modificationEnd;
    }


    public ModuleModification(String modificationType,
                              Date modificationStart, Date modificationEnd) {
        super();
        setID(generateNDigitID(ID_LENGTH));
        this.modificationType = modificationType;
        this.modificationStart = modificationStart;
        this.modificationEnd = modificationEnd;
    }


    public ArrayList<HumanResource> getModifiers() {
        return ModuleModificationDAO.getInstance().getModifiers(getID());
    }

    public boolean addModifier(HumanResource modifier) {
        return ModuleModificationDAO.getInstance().addModifier(getID(), modifier);
    }

    public String getModificationType() {
        return modificationType;
    }

    public boolean setModificationType(String modificationType) {
        this.modificationType = modificationType;
        return ModuleModificationDAO.getInstance().update(new ModuleModification(getID(), modificationType,
                getModificationStart(), getModificationEnd()));
    }

    public Date getModificationStart() {
        return modificationStart;
    }

    public boolean setModificationStart(Date modificationStart) {
        this.modificationStart = modificationStart;
        ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
        return modDAO
                .update(new ModuleModification(getID(), getModificationType(),
                        modificationStart, getModificationEnd()));
    }

    public Date getModificationEnd() {
        return modificationEnd;
    }

    public boolean setModificationEnd(Date modificationEnd) {
        this.modificationEnd = modificationEnd;
        ModuleModificationDAO modDAO = ModuleModificationDAO.getInstance();
        return modDAO
                .update(new ModuleModification(getID(), getModificationType(),
                        getModificationStart(), modificationEnd));
    }

    @Override
    public String toString() {
        return "ID=" + getID() + ", modificationType=" + modificationType +
                ", modificationStart=" + modificationStart +
                ", modificationEnd=" + modificationEnd;
    }
}
