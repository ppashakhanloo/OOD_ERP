package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import project.System;

public class SystemDAO implements DAO<System> {

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static SystemDAO systemDAO;

	private SystemDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static SystemDAO getInstance() {
		if (systemDAO == null) {
			systemDAO = new SystemDAO();
		}
		return systemDAO;
	}

	@Override
	public boolean add(System item) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean add(System item, String ProjectID) {
		ArrayList<String> cols = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		cols.add("ID");
		cols.add("name");
		cols.add("ProjectID");
		values.add(item.getID());
		values.add(item.getName());
		values.add(ProjectID);
		String query = generator.insert("system", cols, values);
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public System get(String key) {
		String query = generator.select("system", null, "ID = " + key);
		try {
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				System newRes = fillSystem(rs);
				return newRes;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private System fillSystem(ResultSet rs) throws SQLException {
		System newSys = new System();
		newSys = new System(rs.getString("ID"), rs.getString("name"));
		return newSys;
	}

	@Override
	public void remove(String key) {
		String query = "DELETE FROM system WHERE ID = " + key + ";";
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(System item) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<System> list() {
		ArrayList<System> systems = new ArrayList<>();
		try {
			ResultSet rs = myStmt.executeQuery("SELECT * FROM system;");
			while (rs.next()) {
				System newSys = fillSystem(rs);
				systems.add(newSys);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return systems;
	}

	public ArrayList<System> getByProjectID(String pid) {
		ArrayList<System> systems = new ArrayList<>();
		try {
			ResultSet rs = myStmt.executeQuery(generator.select("system", null,
					"ProjectID = " + pid));
			while (rs.next()) {
				if (rs.getString("ProjectID").equals(pid))
					systems.add(fillSystem(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return systems;
	}

	public static void main(String[] args) {
		// SystemDAO dao = new SystemDAO();
		// java.lang.System.out.println(dao.getByProjectID("1").get(0).getID());
	}

}