package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import project.*;
import project.System;
import resource.HumanResource;
import unit.Unit;

public class ProjectDAO implements DAO<Project> {

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static ProjectDAO projectDAO;

	private ProjectDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ProjectDAO getInstance() {
		if (projectDAO == null) {
			projectDAO = new ProjectDAO();
		}
		return projectDAO;
	}

	public ArrayList<Unit> getUnitsByProjectID(String pid){
		ArrayList<Unit> units = new ArrayList<>();
		UnitDAO unitDAO = UnitDAO.getInstance();
		String query = generator.select("project_unit", null, "ProjectID = "+pid);
		try {
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				units.add(unitDAO.get(rs.getString("UnitID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return units;
	}
	
	public HumanResource getProjectManagers(String pid){
		HumanResourceDAO hrDAO = HumanResourceDAO.getInstance();
		String query = generator.select("project_management", null, "ProjectID = "+pid);
		try {
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				return ((HumanResource) hrDAO.get(rs.getString("ManagerID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean setProjectManagers(String pid, String mid){
		HumanResourceDAO hrDAO = HumanResourceDAO.getInstance();
		ArrayList<String> colNames = new ArrayList<>();
		colNames.add("ProjectID");
		colNames.add("ManagerID");
		ArrayList<String> values = new ArrayList<>();
		values.add(pid);
		values.add(mid);
		String query = generator.insert("project_management", colNames, values);
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean add(Project item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Project get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Project item) {
		// TODO Auto-generated method stub

	}

	@Override
	public ArrayList<Project> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
