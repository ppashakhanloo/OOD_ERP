package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Unit item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Unit> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
