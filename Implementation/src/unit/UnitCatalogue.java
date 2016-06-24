package unit;

import java.util.ArrayList;

import database.UnitDAO;

public class UnitCatalogue {

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

	public ArrayList<Unit> list() {
		UnitDAO unitDAO = UnitDAO.getInstance();
		return unitDAO.list();
	}

}
