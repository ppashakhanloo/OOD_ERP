package unit;

import java.util.ArrayList;

import database.UnitDAO;

public class UnitCatalogue {

    private static UnitCatalogue unitCatalogue;

    private UnitCatalogue() {
    }

    public static UnitCatalogue getInstance() {
        if (unitCatalogue == null)
            unitCatalogue = new UnitCatalogue();
        return unitCatalogue;
    }

    public boolean add(Unit unit) {
        UnitDAO unitDAO = UnitDAO.getInstance();
        return unitDAO.add(unit);
    }

    public void remove(Unit unit) {
        UnitDAO unitDAO = UnitDAO.getInstance();
        unitDAO.remove(unit.getID());
    }

    public Unit get(String ID) {
        UnitDAO unitDAO = UnitDAO.getInstance();
        return unitDAO.get(ID);
    }

    public ArrayList<Unit> getAll() {
        UnitDAO unitDAO = UnitDAO.getInstance();
        return unitDAO.list();
    }

}
