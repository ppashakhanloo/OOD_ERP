package database;

import resource.Resource;
import resource.ResourceStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ResourceDAO extends DBConnect {
    private static ResourceDAO resourceDAO;

    ResourceDAO() {
        super("erp.conf");
    }

    public static ResourceDAO getInstance() {
        if (resourceDAO == null) {
            resourceDAO = new ResourceDAO();
        }
        return resourceDAO;
    }

    public static void main(String[] args) {
        // ResourceDAO dao = new ResourceDAO();
        // java.lang.System.out.println(dao.listPresentResources().toString());
        // ArrayList<String> colNames = new ArrayList<>();
        // colNames.add("ID");
        // ArrayList<String> values = new ArrayList<>();
        // values.add("2");
        // dao.queryGenerator.insert("access", colNames, values);
        //
        // System.out.println(
        // dao.add(new HumanResource("pardis", "gheini", "doa", "123456", (new
        // AccessLevelFactory()).getAccessLevel(accessLevelID)), "1", "1"));
    }

    public boolean add(Resource item, String projectID) {
        int isAvailable = 0;
        if (item.isAvailable())
            isAvailable = 1;

        ArrayList<String> colNames = new ArrayList<>();
        colNames.add("ID");
        colNames.add("resourceStatus");
        colNames.add("isAvailable");
        colNames.add("ProjectID");
        ArrayList<String> values = new ArrayList<>();
        values.add(item.getID());
        values.add(item.getResourceStatus().toString());
        values.add(Integer.toString(isAvailable));
        values.add(projectID == null ? "NULL" : projectID);

        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().insert(
                    "resource", colNames, values));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Resource> list() {
        String query = QueryGenerator.getInstance().select("resource", null,
                null);
        ArrayList<Resource> results = new ArrayList<>();
        ResultSet rs;
        try {
            Statement myStmt = getSqlConn().createStatement();
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                Resource newRes = fillResource(rs);
                results.add(newRes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    // SELECT * FROM resource WHERE isAvailable = 1;
    public ArrayList<Resource> listPresentResources() {
        String query = QueryGenerator.getInstance().select("resource", null,
                "isAvailable = 1");
        ArrayList<Resource> results = new ArrayList<>();
        ResultSet rs;
        try {
            Statement myStmt = getSqlConn().createStatement();
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                Resource newRes = fillResource(rs);
                results.add(newRes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public boolean update(Resource item) {
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance()
                    .update("resource", "resourceStatus",
                            item.getResourceStatus().toString(),
                            "ID = " + item.getID()));
            myStmt.executeUpdate("UPDATE resource SET isAvailable = 1 WHERE ID = " + item.getID());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean setProjectID(String key, String pid) {
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update(
                    "resource", "ProjectID", pid, "ID = " + key));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Resource get(String key) {
        String query = QueryGenerator.getInstance().select("resource", null,
                "ID = " + "'" + key + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            if (rs.next()) {
                return fillResource(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean remove(String key) {
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().delete(
                    "resource", "ID = " + key));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private Resource fillResource(ResultSet rs) throws SQLException {
        ResourceStatus resourceStatus = ResourceStatus.IDLE;
        switch (rs.getString("resourceStatus")) {
            case "IDLE":
                resourceStatus = ResourceStatus.IDLE;
                break;
            case "BUSY":
                resourceStatus = ResourceStatus.BUSY;
                break;
        }

        boolean isAvailable = false;
        switch (rs.getString("isAvailable")) {
            case "1":
                isAvailable = true;
                break;
            case "0":
                isAvailable = false;
                break;
        }

        return new Resource(rs.getString("ID"), resourceStatus, isAvailable);
    }

}
