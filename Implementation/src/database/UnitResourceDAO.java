package database;

import report.UnitResource;
import resource.Resource;
import unit.Unit;
import unit.UnitCatalogue;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class UnitResourceDAO extends DBConnect {

    private static UnitResourceDAO unitResourceDAO;

    private UnitResourceDAO() {
        super("erp.conf");
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
            Statement myStmt = getSqlConn().createStatement();
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
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                Resource tmp = HumanResourceDAO.getInstance().get(rs.getString("ResourceID"));
                if (tmp == null)
                    tmp = PhysicalResourceDAO.getInstance().get(rs.getString("ResourceID"));
                if (tmp == null)
                    tmp = InformationResourceDAO.getInstance().get(rs.getString("ResourceID"));
                if (tmp == null)
                    tmp = MonetaryResourceDAO.getInstance().get(rs.getString("ResourceID"));

                if (tmp != null && tmp.isAvailable()) {
                    unitResources.add(tmp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unitResources;
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
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean update(UnitResource item, String resourceID) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("QUERY: " + QueryGenerator.getInstance().update("unit_resource", "UnitID",
                item.getUnit().getID(), "ResourceID = " + "'" + resourceID + "'"));
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update("unit_resource", "UnitID",
                    item.getUnit().getID(), "ResourceID = " + "'" + resourceID + "'"));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("unit_resource", "additionDate",
                    sdf.format(item.getAdditionDate()), "ResourceID = " + "'" + resourceID + "'"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public Unit getUnitByResourceID(String rid) {
        String query = QueryGenerator.getInstance().select("unit_resource", null, "ResourceID = " + "'"
                + rid + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                if (rs.getDate("removeDate") == null || rs.getDate("removeDate").after(new Date(System.currentTimeMillis())))
                    return UnitCatalogue.getInstance().get(rs.getString("UnitID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
