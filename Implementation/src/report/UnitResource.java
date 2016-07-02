package report;

import database.UnitResourceDAO;
import resource.Resource;
import unit.Unit;

import java.util.Date;
import java.util.Random;

public class UnitResource {
    private String ID;
    private int ID_LENGTH = 6;
    private Date additionDate;
    private Date removeDate;
    private Unit unit;
    private Resource resource;

    public UnitResource() {
        setID(generateNDigitID(ID_LENGTH));
    }

    private String generateNDigitID(int n) {
        Random random = new Random();
        return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
                * 9 * Math.pow(10, n - 1)));
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

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getID() {
        return ID;
    }

    private void setID(String iD) {
        ID = iD;
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

    public Unit getUnit() {
        return this.unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}
