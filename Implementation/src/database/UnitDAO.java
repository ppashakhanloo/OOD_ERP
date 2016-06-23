package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import project.System;
import unit.Unit;
import unit.Unit;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Unit get(String key) {
		String query = generator.select("unit", null, "ID = " + key);
		try {
			ResultSet rs = myStmt.executeQuery(query);
			if(rs.next()) {
				Unit newUnit = fillUnit(rs);
				return newUnit;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean update(Unit item) {
		try {
			myStmt.executeUpdate(generator.update("unit", "name",
					item.getName(), "ID = " + item.getID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ArrayList<Unit> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
