package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import resource.InformationResource;
import resource.Resource;
import resource.ResourceStatus;

public class InformationResourceDAO extends ResourceDAO {

	private InformationResourceDAO() {
		super();
	}

	public static InformationResourceDAO informationResourceDAO;

	public static InformationResourceDAO getInstance() {
		if (informationResourceDAO == null)
			informationResourceDAO = new InformationResourceDAO();
		return informationResourceDAO;
	}

	@Override
	public boolean add(Resource item, String unitID, String projectID) {
		super.add(item, unitID, projectID);
		InformationResource informationResourceItem = (InformationResource) item;

		ArrayList<String> colNames = new ArrayList<>();
		colNames.add("name");
		colNames.add("description");
		colNames.add("ResourceID");

		ArrayList<String> values = new ArrayList<>();
		values.add(informationResourceItem.getName());
		values.add(informationResourceItem.getDescription());
		values.add(informationResourceItem.getID());

		String query = queryGenerator.insert("information_resource", colNames, values);
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
			ResultSet rs = myStmt.executeQuery("SELECT * from information_resource inner join resource on human_resource.ResourceID = resource.ID");
			Resource newRes = null;
			while (rs.next()) {
				newRes = fillInformationResource(rs);
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

	private ArrayList<Resource> listConditional(String cond) {
		String query = "SELECT * from information_resource inner join resource on information_resource.ResourceID = resource.ID" + (cond == null ? "" : " WHERE " + cond);
		ArrayList<Resource> results = new ArrayList<>();
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				Resource newRes = fillInformationResource(rs);
				results.add(newRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	protected Resource fillInformationResource(ResultSet rs) throws SQLException {
		Resource newRes = new InformationResource(rs.getString("name"), rs.getString("description"));
		newRes.setID(rs.getString("ResourceID"));
		newRes.setAvailable(rs.getString("isAvailable").equals("1"));
		newRes.setResourceStatus(rs.getString("resourceStatus").equals("IDLE") ? ResourceStatus.IDLE : ResourceStatus.BUSY);
		return newRes;
	}

	@Override
	public boolean remove(String key) {
		try {
			myStmt.executeUpdate(queryGenerator.delete("information_resource", "ResourceID = " + key));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return super.remove(key) && true;
	}

	@Override
	public boolean update(Resource item) {
		InformationResource informationResourceItem = (InformationResource) item;
		try {
			myStmt.executeUpdate(queryGenerator.update("information_resource", "name",
					informationResourceItem.getName(), "ResourceID = " + informationResourceItem.getID()));
			myStmt.executeUpdate(queryGenerator.update("information_resource", "description",
					informationResourceItem.getDescription(), "ResourceID = " + informationResourceItem.getID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return super.update(item) && true;
	}

	public ArrayList<Resource> getByName(String name) {
		return listConditional("name = " + "'" + name + "'");
	}

	public static void main(String[] args) {
		// InformationResourceDAO dao = new InformationResourceDAO();
		// Resource res = new InformationResource("db", "db description");
		// System.out.println("ADDED: " + dao.add(res, "1", "1"));
		// InformationResource oldRes = (InformationResource)
		// dao.get(res.getID());
		// System.out.println("OLD: " + oldRes);
		// System.out.println("ID: " + oldRes.getID());
		// InformationResource upRes = new InformationResource("==" +
		// oldRes.getName() + "==", oldRes.getDescription());
		// upRes.setID(oldRes.getID());
		// System.out.println("UPDATED: " + dao.update(upRes));
		// InformationResource newRes = (InformationResource)
		// dao.get(res.getID());
		// System.out.println();
		// System.out.println("NEW: " + newRes);
		// System.out.println("REMOVE: " + dao.remove("477801"));
		// System.out.println("LIST: " + dao.getByName("==db=="));
	}

}
