package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import project.Module;

public class ModuleDAO implements DAO<Module>{

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static ModuleDAO moduleDAO;

	private ModuleDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ModuleDAO getInstance() {
		if (moduleDAO == null) {
			moduleDAO = new ModuleDAO();
		}
		return moduleDAO;
	}

	
	@Override
	public boolean add(Module item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Module get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Module item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Module> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
