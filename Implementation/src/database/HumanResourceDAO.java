package database;

import access.AccessLevelFactory;
import access.AccessLevelType;
import resource.ConfirmStatus;
import resource.HumanResource;
import resource.Resource;
import resource.ResourceStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HumanResourceDAO extends ResourceDAO {

    private HumanResourceDAO() {
        super();
    }

    private static HumanResourceDAO humanResourceDAO;

    public static HumanResourceDAO getInstance() {
        if (humanResourceDAO == null)
            humanResourceDAO = new HumanResourceDAO();
        return humanResourceDAO;
    }

    @Override
    public boolean add(Resource item, String projectID) {
        super.add(item, projectID);
        HumanResource humanResourceItem = (HumanResource) item;

        ArrayList<String> colNames = new ArrayList<>();
        colNames.add("firstName");
        colNames.add("lastName");
        colNames.add("expertise");
        colNames.add("password");
        colNames.add("confirmStatus");
        colNames.add("ResourceID");
        colNames.add("AccessLevelType");

        ArrayList<String> values = new ArrayList<>();
        values.add(humanResourceItem.getFirstName());
        values.add(humanResourceItem.getLastName());
        values.add(humanResourceItem.getExpertise());
        values.add(humanResourceItem.getPassword());
        values.add(humanResourceItem.getConfirmStatus().toString());
        values.add(humanResourceItem.getID());
        values.add(humanResourceItem.getAccessLevel().getAccessLevelType().toString());

        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance()
                    .insert("human_resource", colNames, values));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Resource get(String key) {
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt
                    .executeQuery("SELECT * from human_resource inner join resource on human_resource.ResourceID = resource.ID AND human_resource.ResourceID = " + "'" + key + "'");
            Resource newRes = null;
            while (rs.next()) {
                newRes = fillHumanResource(rs);
            }
            return newRes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean remove(String key) {
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().delete("human_resource",
                    "ResourceID = " + key));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return super.remove(key);
    }

    @Override
    public boolean update(Resource item) {
        HumanResource humanResourceItem = (HumanResource) item;
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update("human_resource",
                    "firstName", humanResourceItem.getFirstName(),
                    "ResourceID = " + humanResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("human_resource",
                    "lastName", humanResourceItem.getLastName(),
                    "ResourceID = " + humanResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("human_resource",
                    "expertise", humanResourceItem.getExpertise(),
                    "ResourceID = " + humanResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("human_resource",
                    "password", humanResourceItem.getPassword(),
                    "ResourceID = " + humanResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("human_resource",
                    "confirmStatus", humanResourceItem.getConfirmStatus()
                            .toString(),
                    "ResourceID = " + humanResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("human_resource",
                    "AccessLevelID",
                    humanResourceItem.getAccessLevel().getAccessLevelType().toString(), "ResourceID = "
                            + humanResourceItem.getID()));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return super.update(item);
    }

    public ArrayList<Resource> getResourcesByProjectID(String pid) {
        ArrayList<Resource> resources = new ArrayList<>();
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt
                    .executeQuery("SELECT * from human_resource inner join resource on human_resource.ResourceID = resource.ID AND ProjectID = "
                            + "'" + pid + "'");
            while (rs.next()) {
                resources.add(fillHumanResource(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resources;
    }

    @Override
    public ArrayList<Resource> list() {
        return listConditional(null);
    }

    private ArrayList<Resource> listConditional(String cond) {
        String query = "SELECT * from human_resource inner join resource on human_resource.ResourceID = resource.ID"
                + (cond == null ? "" : " WHERE " + cond);
        ArrayList<Resource> results = new ArrayList<>();
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
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

    public ArrayList<Resource> getByFirstName(String firstName) {
        return listConditional("firstName = " + "'" + firstName + "'");
    }

    public ArrayList<Resource> getByLastName(String lastName) {
        return listConditional("lastName = " + "'" + lastName + "'");
    }

    public ArrayList<Resource> getByExpertise(String expertise) {
        return listConditional("expertise LIKE " + "'%" + expertise + "%'");
    }

    public ArrayList<Resource> getByConfirmStatus(ConfirmStatus confirmStatus) {
        return listConditional("confirmStatus = " + "'"
                + confirmStatus.toString() + "'");
    }

    private boolean authenticate(String ID, String password) {
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
            rs = myStmt.executeQuery(QueryGenerator.getInstance().select("human_resource",
                    null, "ResourceID = " + ID + " AND " + "password = " + "'"
                            + password + "'"));
            return (rs.next());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String ID, String password) {
        if (authenticate(ID, password)) {
            try {
                Statement myStmt = sqlConn.createStatement();
                myStmt.executeUpdate(QueryGenerator.getInstance().update("human_resource",
                        "logged_in", "1", "ResourceID = " + ID));
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    public void logout(String ID) {
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update("human_resource",
                    "logged_in", "0", "ResourceID = " + ID));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Resource fillHumanResource(ResultSet rs) throws SQLException {
        String accessLevelType = rs.getString("AccessLevelType");
        Resource newRes = new HumanResource(rs.getString("firstName"),
                rs.getString("lastName"), rs.getString("expertise"),
                rs.getString("password"),
                (new AccessLevelFactory()).getAccessLevel(AccessLevelType.valueOf(accessLevelType)));
        newRes.setID(rs.getString("ResourceID"));
        newRes.setAvailable(rs.getString("isAvailable").equals("1"));
        newRes.setResourceStatus(rs.getString("resourceStatus").equals("IDLE") ? ResourceStatus.IDLE
                : ResourceStatus.BUSY);
        return newRes;
    }

    public static void main(String[] args) {
        HumanResourceDAO dao = new HumanResourceDAO();
        // System.out.println(dao.authenticate("102664", "989"));
        Resource res = new HumanResource("sara", "pasha", "java", "888",
                (new AccessLevelFactory()).getAccessLevel(AccessLevelType.Medium));
        System.out.println("ADDED: " + dao.add(res, ""));
        // HumanResource oldRes = (HumanResource) dao.get(res.getID());
        // System.out.println("OLD: " + oldRes);
        // System.out.println("ID: " + oldRes.getID());
        // HumanResource upRes = new HumanResource(oldRes.getFirstName(),
        // oldRes.getLastName(), "android, java",
        // oldRes.getPassword(), oldRes.getAccessLevel());
        // upRes.setID(oldRes.getID());
        // System.out.println("UPDATED: " + dao.update(upRes));
        // HumanResource newRes = (HumanResource) dao.get(res.getID());
        // System.out.println();
        // System.out.println("NEW: " + newRes);
        // // System.out.println("REMOVE: " + dao.remove("980920"));
        // System.out.println("LIST: " + dao.getByExpertise("java"));
    }
}
