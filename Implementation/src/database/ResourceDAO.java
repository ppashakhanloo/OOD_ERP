package database;

import resource.Resource;
import resource.ResourceStatus;

import java.sql.*;
import java.util.ArrayList;

public class ResourceDAO {
	protected Connection sqlConn;
	protected Statement myStmt;
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "0440448182";

	private static ResourceDAO resourceDAO;
	protected QueryGenerator queryGenerator;

	protected ResourceDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
			queryGenerator = QueryGenerator.getInstance();
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

	public boolean add(Resource item, String unitID, String projectID) {
		int isAvailable = 0;
		if (item.isAvailable())
			isAvailable = 1;

		ArrayList<String> colNames = new ArrayList<>();
		colNames.add("ID");
		colNames.add("resourceStatus");
		colNames.add("isAvailable");
		colNames.add("UnitID");
		// colNames.add("ProjectID");
		ArrayList<String> values = new ArrayList<>();
		values.add(item.getID());
		values.add(item.getResourceStatus().toString());
		values.add(Integer.toString(isAvailable));
		values.add(unitID);
		// values.add(projectID);
		String query = queryGenerator.insert("resource", colNames, values);

		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<Resource> list() {
		String query = queryGenerator.select("resource", null, null);
		ArrayList<Resource> results = new ArrayList<>();
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				Resource newRes = fillResource(rs);
				results.add(newRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	public boolean update(Resource item) {
		try {
			myStmt.executeUpdate(queryGenerator.update("resource",
					"resourceStatus", item.getResourceStatus().toString(),
					"ID = " + item.getID()));
			myStmt.executeUpdate(queryGenerator.update("resource",
					"isAvailable", (item.isAvailable() ? "1" : "0"), "ID = "
							+ item.getID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Resource get(String key) {
		return null;
	}

	public boolean remove(String key) {
		try {
			myStmt.executeUpdate(queryGenerator.delete("resource", "ID = "
					+ key));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private Resource fillResource(ResultSet rs) throws SQLException {
		ResourceStatus resourceStatus = ResourceStatus.IDLE;
		switch (rs.getString("resourceStatus")) {
		case "IDLE":
			resourceStatus = ResourceStatus.IDLE;
			break;
		case "BUSY":
			resourceStatus = ResourceStatus.BUSY;
			break;
		}

		boolean isAvailable = false;
		switch (rs.getString("isAvailable")) {
		case "1":
			isAvailable = true;
			break;
		case "0":
			isAvailable = false;
			break;
		}

		return new Resource(rs.getString("ID"), resourceStatus, isAvailable);
	}

	public static void main(String[] args) {
		// ResourceDAO dao = new ResourceDAO();
		// ArrayList<String> colNames = new ArrayList<>();
		// colNames.add("ID");
		// ArrayList<String> values = new ArrayList<>();
		// values.add("2");
		// dao.queryGenerator.insert("access", colNames, values);
		//
		// System.out.println(
		// dao.add(new HumanResource("pardis", "gheini", "doa", "123456", (new
		// AccessLevelFactory()).getAccessLevel(accessLevelID)), "1", "1"));
	}

}
