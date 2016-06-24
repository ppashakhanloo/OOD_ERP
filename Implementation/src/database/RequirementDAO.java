package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import resource.Resource;
import unit.Requirement;

public class RequirementDAO implements DAO<Requirement> {

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static RequirementDAO requirementDAO;

	private RequirementDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static RequirementDAO getInstance() {
		if (requirementDAO == null) {
			requirementDAO = new RequirementDAO();
		}
		return requirementDAO;
	}

	@Override
	public boolean add(Requirement item) {
		return false;
	}

	public boolean add(Requirement item, String rid, String uid) {
		String query = "INSERT INTO requirement (ID, description, provideDate, ResourceID, UnitID) VALUES('"
				+ item.getID()
				+ "', '"
				+ item.getDescription()
				+ "', "
				+ item.getProvideDate() + ", '" + rid + "', '" + uid + "'";
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Requirement get(String key) {
		String query = generator.select("requirement", null, "ID = " + "'"
				+ key + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			if (rs.next()) {
				return fillRequirement(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Requirement fillRequirement(ResultSet rs) {
		try {
			return (new Requirement(rs.getString("ID"),
					rs.getString("description"), rs.getDate("provideDate")));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Resource getResource(String key) {
		String query = generator.select("requirement", null, "ID = " + "'"
				+ key + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			ResourceDAO resourceDAO = ResourceDAO.getInstance();
			return resourceDAO.get(rs.getString("ResourceID"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void setResource(String reqID, String ResourceID) {
		String query = "UPDATE requirement SET ResourceID = " + "'"
				+ ResourceID + "'" + "WHERE ID = " + "'" + reqID + "';";
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void remove(String key) {
		String query = generator.delete("requirement", "ID = " + "'" + key
				+ "'");
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean update(Requirement item) {
		try {
			myStmt.executeUpdate(generator.update("requirement", "description",
					item.getDescription(), "ID = " + "'" + item.getID() + "'"));
			myStmt.executeUpdate("UPDATE requirement SET provideDate  = "
					+ item.getProvideDate() + " WHERE ID = " + "'"
					+ item.getID() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public ArrayList<Requirement> list() {
		ArrayList<Requirement> reqs = new ArrayList<>();
		try {
			ResultSet rs = myStmt.executeQuery("SELECT * FROM requirement;");
			while (rs.next()) {
				reqs.add(fillRequirement(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reqs;
	}

}