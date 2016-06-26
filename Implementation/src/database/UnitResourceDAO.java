package database;

import report.UnitResource;
import resource.Resource;
import unit.Unit;

import java.sql.*;
import java.util.ArrayList;

public class UnitResourceDAO implements DAO<UnitResource> {

	private Connection sqlConn;
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "0440448182";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static UnitResourceDAO unitresDAO;

	private UnitResourceDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
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
			Statement myStmt = sqlConn.createStatement();
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
			Statement myStmt = sqlConn.createStatement();
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
			Statement myStmt = sqlConn.createStatement();
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
			Statement myStmt = sqlConn.createStatement();
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
		return false;
	}

	public boolean add(UnitResource item, String resourceID, String uid) {
		String query = "INSERT INTO unit_resource (ID, additionDate, removeDate, ResourceID, UnitID) VALUES('"
				+ item.getID()
				+ "', "
				+ item.getAdditionDate()
				+ ", "
				+ item.getRemoveDate()
				+ ", '"
				+ resourceID
				+ "', '"
				+ uid
				+ "');";
		try {
			Statement myStmt = sqlConn.createStatement();
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public UnitResource get(String key) {
		String query = generator.select("unit_resource", null, "ID = " + "'"
				+ key + "'");
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				return fillUnitRes(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private UnitResource fillUnitRes(ResultSet rs) throws SQLException {
		UnitResource tmp = new UnitResource(rs.getString("ID"),
				rs.getDate("additionDate"), rs.getDate("removeDate"));
		return tmp;
	}

	@Override
	public void remove(String key) {
		String query = "DELETE FROM unit_resource WHERE ID = " + "'" + key
				+ "'" + ";";
		try {
			Statement myStmt = sqlConn.createStatement();
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean update(UnitResource item) {
		try {
			Statement myStmt = sqlConn.createStatement();
			myStmt.executeUpdate("UPDATE unit_resource SET additionDate  = "
					+ item.getAdditionDate() + " WHERE ID = " + "'"
					+ item.getID() + "'");
			myStmt.executeUpdate("UPDATE unit_resource SET removeDate  = "
					+ item.getRemoveDate() + " WHERE ID = " + "'"
					+ item.getID() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public ArrayList<UnitResource> list() {
		ArrayList<UnitResource> systems = new ArrayList<>();
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt.executeQuery("SELECT * FROM unit_resource;");
			while (rs.next()) {
				UnitResource newSys = fillUnitRes(rs);
				systems.add(newSys);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return systems;
	}

}
