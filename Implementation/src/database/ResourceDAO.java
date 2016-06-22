package database;

import java.util.ArrayList;

import resource.Resource;

import java.sql.*;

public class ResourceDAO implements DAO<Resource> {
	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "0440448182";

	private static ResourceDAO resourceDAO;

	private ResourceDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResourceDAO getInstance() {
		if (resourceDAO == null) {
			resourceDAO = new ResourceDAO();
		}
		return resourceDAO;
	}

	@Override
	public boolean add(Resource item) {
		// String query = "insert into Resource (ID, resourceStatus,
		// isAvailable, unitID, ) "
		return false;
	}

	public boolean add(Resource item, String unitID, String projectID) {
		int isAvailable = 0;
		if (item.isAvailable())
			isAvailable = 1;
		String query = "insert into Resource (ID, resourceStatus, isAvailable, Unit_ID, Project_ID) " + "values (" + "'"
				+ item.getID() + "'" + ", " + "'" + item.getResourceStatus().toString() + "'" + ", " + "'" + isAvailable
				+ "'" + ", " + "'" + unitID + "'" + ", " + "'" + projectID + "'" + ")";

		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Resource get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Resource> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Resource item) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		ResourceDAO dao = new ResourceDAO();
		
	}
}
