package unit;

import database.UnitDAO;

import java.util.ArrayList;

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

    public Unit get(String ID) {
        UnitDAO unitDAO = UnitDAO.getInstance();
        return unitDAO.get(ID);
    }

    public ArrayList<Unit> list() {
        UnitDAO unitDAO = UnitDAO.getInstance();
        return unitDAO.list();
    }
}
