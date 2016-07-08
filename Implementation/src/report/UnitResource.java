package report;

import database.UnitResourceDAO;
import resource.Resource;
import unit.Unit;
import utility.Identifiable;

import java.util.Date;

public class UnitResource extends Identifiable {
    private Date additionDate;
    private Date removeDate;
    private Unit unit;
    private Resource resource;

    public UnitResource() {
        setID(generateNDigitID(ID_LENGTH));
    }

    public UnitResource(String iD, Date additionDate, Date removeDate, Unit unit) {
        setID(iD);
        this.additionDate = additionDate;
        this.removeDate = removeDate;
        this.unit = unit;
    }

    public UnitResource(Date additionDate, Date removeDate, Unit unit) {
        setID(generateNDigitID(ID_LENGTH));
        this.additionDate = additionDate;
        this.removeDate = removeDate;
        this.unit = unit;
    }

    public Date getAdditionDate() {
        return additionDate;
    }

    public boolean setAdditionDate(Date additionDate) {
        this.additionDate = additionDate;
        UnitResourceDAO dao = UnitResourceDAO.getInstance();
        return dao
                .update(new UnitResource(null, additionDate, getRemoveDate(), getUnit()));
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public boolean setRemoveDate(Date removeDate) {
        this.removeDate = removeDate;
        return UnitResourceDAO.getInstance()
                .update(new UnitResource(null, getAdditionDate(), removeDate, getUnit()));
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
