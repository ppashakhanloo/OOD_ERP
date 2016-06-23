package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import access.AccessLevelFactory;
import resource.HumanResource;
import resource.Resource;

public class HumanResourceDAO extends ResourceDAO {

	public HumanResourceDAO() {
		super();
	}

	@Override
	public boolean add(Resource item, String unitID, String projectID) {
		super.add(item, unitID, projectID);
		HumanResource humanResourceItem = (HumanResource) item;

		ArrayList<String> colNames = new ArrayList<>();
		colNames.add("firstName");
		colNames.add("lastName");
		colNames.add("expertise");
		colNames.add("password");
		colNames.add("confirmStatus");
		colNames.add("ResourceID");
		colNames.add("AccessLevelID");

		ArrayList<String> values = new ArrayList<>();
		values.add(humanResourceItem.getFirstName());
		values.add(humanResourceItem.getLastName());
		values.add(humanResourceItem.getExpertise());
		values.add(humanResourceItem.getPassword());
		values.add(humanResourceItem.getConfirmStatus().toString());
		values.add(humanResourceItem.getID());
		values.add(humanResourceItem.getAccessLevel().getID());

		String query = queryGenerator.insert("human_resource", colNames, values);
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
			ResultSet rs = myStmt.executeQuery(queryGenerator.select("human_resource", null, "ResourceID = " + key));
			while (rs.next()) {
				Resource newRes = fillHumanResource(rs);
				return newRes;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean remove(String key) {
		try {
			myStmt.executeUpdate(queryGenerator.delete("human_resource", "ResourceID = " + key));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return super.remove(key) && true;
	}

	@Override
	public boolean update(Resource item) {
		HumanResource humanResourceItem = (HumanResource) item;
		try {
			myStmt.executeUpdate(queryGenerator.update("human_resource", "firstName", humanResourceItem.getFirstName(),
					"ResourceID = " + humanResourceItem.getID()));
			myStmt.executeUpdate(queryGenerator.update("human_resource", "lastName", humanResourceItem.getLastName(),
					"ResourceID = " + humanResourceItem.getID()));
			myStmt.executeUpdate(queryGenerator.update("human_resource", "expertise", humanResourceItem.getExpertise(),
					"ResourceID = " + humanResourceItem.getID()));
			myStmt.executeUpdate(queryGenerator.update("human_resource", "password", humanResourceItem.getPassword(),
					"ResourceID = " + humanResourceItem.getID()));
			myStmt.executeUpdate(queryGenerator.update("human_resource", "confirmStatus",
					humanResourceItem.getConfirmStatus().toString(), "ResourceID = " + humanResourceItem.getID()));
			myStmt.executeUpdate(queryGenerator.update("human_resource", "AccessLevelID",
					humanResourceItem.getAccessLevel().getID(), "ResourceID = " + humanResourceItem.getID()));
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return super.update(item) && true;
	}

	@Override
	public ArrayList<Resource> list() {
		String query = queryGenerator.select("human_resource", null, null);
		ArrayList<Resource> results = new ArrayList<>();
		ResultSet rs;
		try {
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				Resource newRes = fillHumanResource(rs);
				results.add(newRes);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}

	private Resource fillHumanResource(ResultSet rs) throws SQLException {
		Resource newRes = new HumanResource();
		String accessLevelID = rs.getString("AccessLevelID");
		newRes = new HumanResource(rs.getString("firstName"), rs.getString("lastName"), rs.getString("expertise"),
				rs.getString("password"), (new AccessLevelFactory()).getAccessLevel(accessLevelID));
		newRes.setID(rs.getString("ResourceID"));
		return newRes;
	}

	public static void main(String[] args) {
		// HumanResourceDAO dao = new HumanResourceDAO();
		//
		// Resource res = new HumanResource("pardis", "pasha", "java", "888",
		// (new AccessLevelFactory()).getAccessLevel("2"));
		// System.out.println("ADDED: " + dao.add(res, "1", "1"));
		// HumanResource oldRes = (HumanResource) dao.get(res.getID());
		// System.out.println("OLD: " + oldRes);
		// System.out.println("ID: " + oldRes.getID());
		// HumanResource upRes = new HumanResource(oldRes.getFirstName(),
		// oldRes.getLastName(), "android",
		// oldRes.getPassword(), oldRes.getAccessLevel());
		// upRes.setID(oldRes.getID());
		// System.out.println("UPDATED: " + dao.update(upRes));
		// HumanResource newRes = (HumanResource) dao.get(res.getID());
		// System.out.println();
		// System.out.println("NEW: " + newRes);
		// System.out.println("REMOVE: " + dao.remove("980920"));
		// System.out.println("LIST: " + dao.list());
	}
}
