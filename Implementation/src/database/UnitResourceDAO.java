package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import report.UnitResource;
import resource.Resource;

public class UnitResourceDAO implements DAO<UnitResource> {

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

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