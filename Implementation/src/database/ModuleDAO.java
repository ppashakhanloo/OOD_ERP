package database;

import project.Module;
import resource.HumanResource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ModuleDAO implements DAO<Module> {

    private static ModuleDAO moduleDAO;
    private Connection sqlConn;
    private String url = "jdbc:mysql://localhost:9999/erp?useUnicode=true&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "28525336";
    private QueryGenerator generator = QueryGenerator.getInstance();

    private ModuleDAO() {
        try {
            sqlConn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(query);
            ArrayList<String> cols = new ArrayList<>();
            ArrayList<String> values = new ArrayList<>();
            cols.add("ModuleID");
            cols.add("SystemID");
            values.add(item.getID());
            values.add(systemID);
            String query2 = generator.insert("module_system", cols, values);
            myStmt.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Module get(String key) {
        String query = generator.select("module", null, "ID = " + "'" + key
                + "'");
        try {
            Statement myStmt = sqlConn.createStatement();
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
    public void remove(String key) {
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate("DELETE FROM module WHERE ID = " + "'" + key
                    + "'" + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(Module item) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(generator.update("module", "name",
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

    public ArrayList<Module> getByName(String name) {
        ArrayList<Module> modules = new ArrayList<>();
        String query = generator.select("module", null, "name = " + "'" + name
                + "'");
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                modules.add(fillModule(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public ArrayList<Module> getByDevelopmentStart(String DevelopmentStart) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	ArrayList<Module> modules = new ArrayList<>();
        String query = generator.select("module", null, "developmentStart = "
        		 + "'"
                 + (DevelopmentStart == null ? "0000-00-00" : sdf
                 .format(DevelopmentStart)) + "'");
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                modules.add(fillModule(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public ArrayList<Module> getByDevelopmentEnd(String DevelopmentEnd) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	ArrayList<Module> modules = new ArrayList<>();
        String query = generator.select("module", null, "developmentEnd = "
        		 + "'"
                 + (DevelopmentEnd == null ? "0000-00-00" : sdf
                 .format(DevelopmentEnd)) + "'");
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                modules.add(fillModule(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    @Override
    public ArrayList<Module> list() {
        ArrayList<Module> modules = new ArrayList<>();
        try {
            Statement myStmt = sqlConn.createStatement();
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

    public ArrayList<HumanResource> getDevelopers(String modID) {
        HumanResourceDAO hrDAO = HumanResourceDAO.getInstance();
        ArrayList<HumanResource> developers = new ArrayList<>();
        String query = generator.select("module_humanresource", null,
                "ModuleID = " + "'" + modID + "'");
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                developers.add((HumanResource) hrDAO.get(rs
                        .getString("HumanResourceID")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    public boolean addDeveloper(String modID, HumanResource developer) {
        ArrayList<String> colNames = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        colNames.add("ModuleID");
        colNames.add("HumanResourceID");
        values.add(modID);
        values.add(developer.getID());
        try {
            Statement myStmt = sqlConn.createStatement();
            System.out.println("QUERY: "
                    + generator
                    .insert("module_humanresource", colNames, values));
            myStmt.executeUpdate(generator.insert("module_humanresource",
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
