package report;

import unit.Unit;
import utility.Identifiable;

import java.util.Date;

public class UnitResource extends Identifiable {
    private Date additionDate;
    private Date removeDate;
    private Unit unit;

    public UnitResource(Date additionDate, Date removeDate, Unit unit) {
        setID(generateNDigitID(ID_LENGTH));
        this.additionDate = additionDate;
        this.removeDate = removeDate;
        this.unit = unit;
    }

    public Date getAdditionDate() {
        return additionDate;
    }

    public Date getRemoveDate() {
        return removeDate;
    }

    public Unit getUnit() {
        return this.unit;
    }
}
