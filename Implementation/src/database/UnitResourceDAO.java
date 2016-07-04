package database;

import report.UnitResource;
import resource.Resource;
import unit.Unit;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UnitResourceDAO {

    private Connection sqlConn;
    private String url = "jdbc:mysql://localhost:3306/erp?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "";

    private static UnitResourceDAO unitResourceDAO;

    private UnitResourceDAO() {
        try {
            sqlConn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UnitResourceDAO getInstance() {
        if (unitResourceDAO == null) {
            unitResourceDAO = new UnitResourceDAO();
        }
        return unitResourceDAO;
    }

    public ArrayList<Resource> getResourceByUnitID(String uid) {
        ArrayList<Resource> unitResources = new ArrayList<>();
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt.executeQuery(QueryGenerator.getInstance().select("unit_resource", null, "UnitID = "
                    + "'" + uid + "'"));

            while (rs.next()) {
                String resourceID = rs.getString("ResourceID");
                Resource resource = HumanResourceDAO.getInstance().get(resourceID);
                if (resource == null)
                    resource = PhysicalResourceDAO.getInstance().get(resourceID);
                if (resource == null)
                    resource = InformationResourceDAO.getInstance().get(resourceID);
                if (resource == null)
                    resource = MonetaryResourceDAO.getInstance().get(resourceID);
                unitResources.add(resource);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unitResources;
    }

    public ArrayList<Resource> getAvailableResourceByUnitID(String uid) {
        ArrayList<Resource> unitResources = new ArrayList<>();
        String query = QueryGenerator.getInstance().select("unit_resource", null, "UnitID = "
                + "'" + uid + "'");
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                Resource tmp = HumanResourceDAO.getInstance().get(rs.getString("ResourceID"));
                if (tmp == null)
                    tmp = PhysicalResourceDAO.getInstance().get(rs.getString("ResourceID"));
                if (tmp == null)
                    tmp = InformationResourceDAO.getInstance().get(rs.getString("ResourceID"));
                if (tmp == null)
                    tmp = MonetaryResourceDAO.getInstance().get(rs.getString("ResourceID"));

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
        String query = QueryGenerator.getInstance().select("unit_resource", null, "ID = " + "'"
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
        String query = QueryGenerator.getInstance().select("unit_resource", null, "ID = " + "'"
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


    public boolean add(UnitResource item, String resourceID) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String query = "INSERT INTO unit_resource (ID, additionDate, removeDate, ResourceID, UnitID) VALUES("
                + "'" + item.getID() + "'"
                + ", "
                + "'" + sdf.format(item.getAdditionDate()) + "'"
                + ", "
                + "'" + (item.getRemoveDate() == null ? "0000-00-00" : sdf.format(item.getRemoveDate())) + "'"
                + ", "
                + "'" + resourceID + "'"
                + ", "
                + "'" + item.getUnit().getID() + "'"
                + ");";
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public UnitResource get(String key) {
        String query = QueryGenerator.getInstance().select("unit_resource", null, "ID = " + "'"
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
        return new UnitResource(rs.getString("ID"),
                rs.getDate("additionDate"), rs.getDate("removeDate"), UnitDAO.getInstance().get(rs.getString("UnitID")));
    }

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
