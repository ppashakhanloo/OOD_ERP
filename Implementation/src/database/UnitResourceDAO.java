package database;

import report.UnitResource;
import resource.Resource;
import unit.Unit;

import java.sql.*;
import java.util.ArrayList;

public class UnitResourceDAO implements DAO<UnitResource> {

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "0440448182";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static UnitResourceDAO unitresDAO;

	private UnitResourceDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static UnitResourceDAO getInstance() {
		if (unitresDAO == null) {
			unitresDAO = new UnitResourceDAO();
		}
		return unitresDAO;
	}

	public ArrayList<Resource> getResourceByUnitID(String uid) {
		ArrayList<Resource> unitResources = new ArrayList<>();
		ResourceDAO resourceDAO = ResourceDAO.getInstance();
		String query = generator.select("unit_resource", null, "UnitID = "
				+ "'" + uid + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				unitResources.add(resourceDAO.get(rs.getString("ResourceID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unitResources;
	}

	public ArrayList<Resource> getAvailableResourceByUnitID(String uid) {
		ArrayList<Resource> unitResources = new ArrayList<>();
		ResourceDAO resourceDAO = ResourceDAO.getInstance();
		String query = generator.select("unit_resource", null, "UnitID = "
				+ "'" + uid + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				Resource tmp = resourceDAO.get(rs.getString("ResourceID"));
				if (tmp.isAvailable()) {
					unitResources.add(tmp);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unitResources;
	}

	public Resource getResource(String key) {
		String query = generator.select("unit_resource", null, "ID = " + "'"
				+ key + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			ResourceDAO dao = ResourceDAO.getInstance();
			return dao.get(rs.getString("ResourceID"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Unit getUnit(String key) {
		String query = generator.select("unit_resource", null, "ID = " + "'"
				+ key + "'");
		try {
			ResultSet rs = myStmt.executeQuery(query);
			UnitDAO dao = UnitDAO.getInstance();
			return dao.get(rs.getString("UnitID"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean add(UnitResource item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UnitResource get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean update(UnitResource item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<UnitResource> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
