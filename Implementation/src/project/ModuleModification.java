package project;

import database.ModuleModificationDAO;
import resource.HumanResource;
import utility.Identifiable;

import java.util.Date;

public class ModuleModification extends Identifiable {
    private String modificationType;
    private Date modificationStart;
    private Date modificationEnd;

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


    public boolean addModifier(HumanResource modifier) {
        return ModuleModificationDAO.getInstance().addModifier(getID(), modifier);
    }

    public String getModificationType() {
        return modificationType;
    }

    public Date getModificationStart() {
        return modificationStart;
    }

    public Date getModificationEnd() {
        return modificationEnd;
    }

    @Override
    public String toString() {
//        return "ID=" + getID() + ", modificationType=" + modificationType +
//                ", modificationStart=" + modificationStart +
//                ", modificationEnd=" + modificationEnd;
        return modificationType + "(" + modificationStart + "-" + modificationEnd + ")";
    }
}
