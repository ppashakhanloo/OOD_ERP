package database;

import resource.PhysicalResource;
import resource.Resource;
import resource.ResourceStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhysicalResourceDAO extends ResourceDAO {
	private PhysicalResourceDAO() {
		super();
	}

	private static PhysicalResourceDAO physicalResourceDAO;

	public static PhysicalResourceDAO getInstance() {
		if (physicalResourceDAO == null)
			physicalResourceDAO = new PhysicalResourceDAO();
		return physicalResourceDAO;
	}

	@Override
	public boolean add(Resource item, String unitID, String projectID) {
		super.add(item, unitID, projectID);
		PhysicalResource physicalResourceItem = (PhysicalResource) item;

		ArrayList<String> colNames = new ArrayList<>();
		colNames.add("name");
		colNames.add("model");
		colNames.add("location");
		colNames.add("ResourceID");

		ArrayList<String> values = new ArrayList<>();
		values.add(physicalResourceItem.getName());
		values.add(physicalResourceItem.getModel());
		values.add(physicalResourceItem.getLocation());
		values.add(physicalResourceItem.getID());

		String query = queryGenerator.insert("physical_resource", colNames, values);
		System.out.println(query);
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
		try {
			ResultSet rs = myStmt.executeQuery("SELECT * from physical_resource inner join resource on human_resource.ResourceID = resource.ID");
			Resource newRes = null;
			while (rs.next()) {
				newRes = fillPhysicalResource(rs);
			}
			return newRes;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public ArrayList<Resource> list() {
		return listConditional(null);
	}

	@Override
	public boolean remove(String key) {
		try {
			myStmt.executeUpdate(queryGenerator.delete("physical_resource", "ResourceID = " + key));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return super.remove(key);
	}

	@Override
	public boolean update(Resource item) {
		PhysicalResource physicalResourceItem = (PhysicalResource) item;
		try {
			myStmt.executeUpdate(queryGenerator.update("physical_resource", "name", physicalResourceItem.getName(),
					"ResourceID = " + physicalResourceItem.getID()));
			myStmt.executeUpdate(queryGenerator.update("physical_resource", "model", physicalResourceItem.getModel(),
					"ResourceID = " + physicalResourceItem.getID()));
			myStmt.executeUpdate(queryGenerator.update("physical_resource", "location",
					physicalResourceItem.getLocation(), "ResourceID = " + physicalResourceItem.getID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return super.update(item);
	}

	public ArrayList<Resource> getByName(String name) {
		return listConditional("name = " + "'" + name + "'");
	}

	public ArrayList<Resource> getByModel(String model) {
		return listConditional("model = " + "'" + model + "'");
	}

	public ArrayList<Resource> getByLocation(String location) {
		return listConditional("location = " + "'" + location + "'");
	}

	private ArrayList<Resource> listConditional(String cond) {
		String query = "SELECT * from physical_resource inner join resource on physical_resource.ResourceID = resource.ID" + (cond == null ? "" : " WHERE " + cond);
		ArrayList<Resource> results = new ArrayList<>();
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				Resource newRes = fillPhysicalResource(rs);
				results.add(newRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	private Resource fillPhysicalResource(ResultSet rs) throws SQLException {
		Resource newRes = new PhysicalResource(rs.getString("name"), rs.getString("model"), rs.getString("location"));
		newRes.setID(rs.getString("ResourceID"));
		newRes.setAvailable(rs.getString("isAvailable").equals("1"));
		newRes.setResourceStatus(rs.getString("resourceStatus").equals("IDLE") ? ResourceStatus.IDLE : ResourceStatus.BUSY);
		return newRes;
	}


	public ArrayList<Resource> getResourcesByProjectID(String pid) {
		ArrayList<Resource> resources = new ArrayList<>();
		try {
			ResultSet rs = myStmt.executeQuery("SELECT * from physical_resource inner join resource on physical_resource.ResourceID = resource.ID AND ProjectID = "+"'" + pid + "'");
			while(rs.next()){
				resources.add(fillPhysicalResource(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resources;
	}

	public static void main(String[] args) {
		// PhysicalResourceDAO dao = new PhysicalResourceDAO();
		// Resource res = new PhysicalResource("table", "nilper", "room202");
		// System.out.println("ADDED: " + dao.add(res, "1", "1"));
		// PhysicalResource oldRes = (PhysicalResource) dao.get(res.getID());
		// System.out.println("OLD: " + oldRes);
		// System.out.println("ID: " + oldRes.getID());
		// PhysicalResource upRes = new PhysicalResource(oldRes.getName(),
		// oldRes.getModel(), oldRes.getLocation());
		// upRes.setID(oldRes.getID());
		// System.out.println("UPDATED: " + dao.update(upRes));
		// PhysicalResource newRes = (PhysicalResource) dao.get(res.getID());
		// System.out.println();
		// System.out.println("NEW: " + newRes);
		// System.out.println("REMOVE: " + dao.remove("802830"));
		// System.out.println("LIST: " + dao.getByLocation("room202"));
	}

}
