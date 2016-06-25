package database;

import unit.Unit;

import java.sql.*;
import java.util.ArrayList;

public class UnitDAO implements DAO<Unit> {

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static UnitDAO unitDAO;

	private UnitDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static UnitDAO getInstance() {
		if (unitDAO == null) {
			unitDAO = new UnitDAO();
		}
		return unitDAO;
	}

	@Override
	public boolean add(Unit item) {
		ArrayList<String> colNames = new ArrayList<>();
		colNames.add("ID");
		colNames.add("name");
		ArrayList<String> values = new ArrayList<>();
		values.add(item.getID());
		values.add(item.getName());
		try {
			myStmt.executeUpdate(generator.insert("unit", colNames, values));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Unit get(String key) {
		String query = generator
				.select("unit", null, "ID = " + "'" + key + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			if (rs.next()) {
				return fillUnit(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Unit> getByName(String name) {
		ArrayList<Unit> units = new ArrayList<>();
		String query = generator.select("unit", null, "name = " + "'" + name
				+ "'");
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				units.add(fillUnit(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return units;
	}

	private Unit fillUnit(ResultSet rs) {
		try {
			return (new Unit(rs.getString("ID"), rs.getString("name")));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void remove(String key) {
		try {
			myStmt.executeUpdate(generator.delete("unit", "ID = " + "'" + key
					+ "'"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean update(Unit item) {
		try {
			myStmt.executeUpdate(generator.update("unit", "name",
					item.getName(), "ID = " + "'" + item.getID() + "'"));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ArrayList<Unit> list() {
		ArrayList<Unit> units = new ArrayList<>();
		try {
			ResultSet rs = myStmt.executeQuery("SELECT * FROM unit;");
			while (rs.next()) {
				Unit newUnit = fillUnit(rs);
				units.add(newUnit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return units;
	}
	//public static void main(String[] args) {
	//	UnitDAO dao = UnitDAO.getInstance();
	//	System.out.println(dao.update(new Unit("1", "test")));
	//}
}
