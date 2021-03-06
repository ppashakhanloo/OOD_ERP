package database;

import resource.Resource;
import unit.Requirement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RequirementDAO extends DBConnect implements DAO<Requirement> {

    private static RequirementDAO requirementDAO;

    private RequirementDAO() {
        super("erp.conf");
    }

    public static RequirementDAO getInstance() {
        if (requirementDAO == null) {
            requirementDAO = new RequirementDAO();
        }
        return requirementDAO;
    }

    @Override
    public boolean add(Requirement item) {
        return false;
    }

    public boolean add(Requirement item, String rid, String uid) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String query = "INSERT INTO requirement (ID, description, provideDate, ResourceID, UnitID) VALUES('"
                + item.getID()
                + "', '"
                + item.getDescription()
                + "', "
                + "'"
                + (item.getProvideDate() == null ? "0000-00-00" : sdf
                .format(item.getProvideDate()))
                + "'"
                + ", '"
                + rid
                + "', '" + uid + "');";
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Requirement get(String key) {
        String query = QueryGenerator.getInstance().select("requirement", null,
                "ID = " + "'" + key + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            if (rs.next()) {
                return fillRequirement(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Requirement fillRequirement(ResultSet rs) {
        try {
            return (new Requirement(rs.getString("ID"),
                    rs.getString("description"), rs.getDate("provideDate")));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Resource getResource(String key) {
        String query = QueryGenerator.getInstance().select("requirement", null,
                "ID = " + "'" + key + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            ResourceDAO resourceDAO = ResourceDAO.getInstance();
            if (rs.next())
                return resourceDAO.get(rs.getString("ResourceID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Requirement item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update(
                    "requirement", "description", item.getDescription(),
                    "ID = " + "'" + item.getID() + "'"));
            myStmt.executeUpdate("UPDATE requirement SET provideDate  = "
                    + "'"
                    + (item.getProvideDate() == null ? "0000-00-00" : sdf
                    .format(item.getProvideDate())) + "'"
                    + " WHERE ID = " + "'" + item.getID() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Requirement> list() {
        ArrayList<Requirement> requirements = new ArrayList<>();
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM requirement;");
            while (rs.next()) {
                requirements.add(fillRequirement(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requirements;
    }

    public ArrayList<Requirement> getRequirementsByUnitID(String uid) {
        ArrayList<Requirement> reqs = new ArrayList<>();
        String query = QueryGenerator.getInstance().select("requirement", null,
                "UnitID = " + "'" + uid + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                reqs.add(fillRequirement(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reqs;
    }

    // public static void main(String[] args) {
    // RequirementDAO dao = RequirementDAO.getInstance();
    // System.out.println(dao.getRequirementsByUnitID("1").get(0).getDescription());
    // }
}
