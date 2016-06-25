package database;

import project.Project;
import report.ProjectRequirement;
import resource.Resource;

import java.sql.*;
import java.util.ArrayList;

public class ProjectRequirementDAO implements DAO<ProjectRequirement> {
	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "0440448182";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static ProjectRequirementDAO prjReqDAO;

	private ProjectRequirementDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ProjectRequirementDAO getInstance() {
		if (prjReqDAO == null) {
			prjReqDAO = new ProjectRequirementDAO();
		}
		return prjReqDAO;
	}

	public Resource getResource(String key) {
		String query = generator.select("project_requirement", null, "ID = "
				+ "'" + key + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			ResourceDAO rsDAO = ResourceDAO.getInstance();
			return rsDAO.get(rs.getString("ResourceID"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean setResource(String key, Resource res) {
		String query = generator.update("project_requirement", "ResourceID",
				res.getID(), "ID = " + "'" + key + "'");
		try {
			myStmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Project getProject(String key) {
		String query = generator.select("project_requirement", null, "ID = "
				+ "'" + key + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			ProjectDAO prjDAO = ProjectDAO.getInstance();
			return prjDAO.get(rs.getString("ProjectID"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean add(ProjectRequirement item) {
		return false;
	}

	public boolean add(ProjectRequirement item, String ProjectID,
			String ResourceID) {
		String query = "INSERT INTO project_requirement (ID, provideDate, releaseDate,"
				+ "isEssential, criticalProvideDate, lengthOfPossession, ProjectID, ResourceID) "
				+ "VALUES ('"
				+ item.getID()
				+ "', "
				+ item.getProvideDate()
				+ ", "
				+ item.getReleaseDate()
				+ ", "
				+ item.isEssential()
				+ ", "
				+ item.getCriticalProvideDate()
				+ ", "
				+ item.getLengthOfPossession()
				+ ", '"
				+ ProjectID
				+ "', '"
				+ ResourceID + "');";
		try {
			myStmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ProjectRequirement get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean update(ProjectRequirement item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<ProjectRequirement> list() {
		// TODO Auto-generated method stub
		return null;
	}
}
