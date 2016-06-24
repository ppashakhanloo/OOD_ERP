package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import project.Project;
import report.ProjectRequirement;
import resource.Resource;

public class ProjectRequirementDAO implements DAO<ProjectRequirement> {
	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

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
		// TODO Auto-generated method stub
		return false;
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
