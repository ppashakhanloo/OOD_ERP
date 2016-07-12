package database;

import project.Module;
import resource.HumanResource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ModuleDAO extends DBConnect implements DAO<Module> {

    private static ModuleDAO moduleDAO;

    private ModuleDAO() {
        super("erp.conf");
    }

    public static ModuleDAO getInstance() {
        if (moduleDAO == null) {
            moduleDAO = new ModuleDAO();
        }
        return moduleDAO;
    }

    @Override
    public boolean add(Module item) {
        return false;
    }

    public boolean add(Module item, String systemID) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String query = "INSERT INTO module (ID, name, developmentStart,developmentEnd) VALUES ('"
                + item.getID()
                + "', '"
                + item.getName()
                + "', "
                + "'"
                + (item.getDevelopmentStart() == null ? "0000-00-00" : sdf
                .format(item.getDevelopmentStart())) + "'"
                + ", "
                + "'"
                + (item.getDevelopmentStart() == null ? "0000-00-00" : sdf
                .format(item.getDevelopmentStart())) + "'"
                + ");";
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(query);
            ArrayList<String> cols = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();
            cols.add("ModuleID");
            cols.add("SystemID");
            values.add(item.getID());
            values.add(systemID);
            String query2 = QueryGenerator.getInstance().insert("module_system", cols, values);
            myStmt.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Module get(String key) {
        String query = QueryGenerator.getInstance().select("module", null, "ID = " + "'" + key
                + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next())
                return fillModule(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Module fillModule(ResultSet rs) throws SQLException {
        return new Module(rs.getString("ID"), rs.getString("name"),
                rs.getDate("developmentStart"), rs.getDate("developmentEnd"));
    }

    @Override
    public boolean update(Module item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update("module", "name",
                    item.getName(), "ID = " + "'" + item.getID() + "'"));
            myStmt.executeUpdate("UPDATE module SET developmentStart  = "
                    + "'"
                    + (item.getDevelopmentStart() == null ? "0000-00-00" : sdf
                    .format(item.getDevelopmentStart())) + "'"
                    + " WHERE ID = " + "'"
                    + item.getID() + "'");
            myStmt.executeUpdate("UPDATE module SET developmentEnd  = "
                    + "'"
                    + (item.getDevelopmentEnd() == null ? "0000-00-00" : sdf
                    .format(item.getDevelopmentEnd())) + "'"
                    + " WHERE ID = " + "'"
                    + item.getID() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public ArrayList<Module> list() {
        ArrayList<Module> modules = new ArrayList<>();
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM module;");
            while (rs.next()) {
                Module newMod = fillModule(rs);
                modules.add(newMod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public boolean addDeveloper(String modID, HumanResource developer) {
        ArrayList<String> colNames = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        colNames.add("ModuleID");
        colNames.add("HumanResourceID");
        values.add(modID);
        values.add(developer.getID());
        try {
            Statement myStmt = getSqlConn().createStatement();
            System.out.println("QUERY: "
                    + QueryGenerator.getInstance()
                    .insert("module_humanresource", colNames, values));
            myStmt.executeUpdate(QueryGenerator.getInstance().insert("module_humanresource",
                    colNames, values));

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    // public static void main(String[] args) {
    // ModuleDAO dao = ModuleDAO.getInstance();
    // HumanResourceDAO hr = HumanResourceDAO.getInstance();
    // dao.addDeveloper("A1", (HumanResource) hr.get("2"));
    // System.out.println(dao.getDevelopers("A1").get(0).getFirstName());
    // }
}
