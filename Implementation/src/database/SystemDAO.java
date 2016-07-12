package database;

import project.Module;
import project.System;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SystemDAO extends DBConnect implements DAO<System> {

    private static SystemDAO systemDAO;

    private SystemDAO() {
        super("erp.conf");
    }

    public static SystemDAO getInstance() {
        if (systemDAO == null) {
            systemDAO = new SystemDAO();
        }
        return systemDAO;
    }

    @Override
    public boolean add(System item) {
        return false;
    }

    public boolean add(System item, String ProjectID) {
        ArrayList<String> cols = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        cols.add("ID");
        cols.add("name");
        cols.add("ProjectID");
        values.add(item.getID());
        values.add(item.getName());
        values.add(ProjectID);
        String query = QueryGenerator.getInstance().insert("system", cols, values);
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
    public System get(String key) {
        String query = QueryGenerator.getInstance().select("system", null, "ID = " + "'" + key
                + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                System newRes = fillSystem(rs);
                return newRes;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private System fillSystem(ResultSet rs) throws SQLException {
        System newSys = new System(rs.getString("ID"), rs.getString("name"));
        return newSys;
    }

    @Override
    public boolean update(System item) {
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update("system", "name",
                    item.getName(), "ID = " + "'" + item.getID() + "'"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<System> list() {
        ArrayList<System> systems = new ArrayList<>();
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM system;");
            while (rs.next()) {
                System newSys = fillSystem(rs);
                systems.add(newSys);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return systems;
    }

    public ArrayList<System> getByProjectID(String pid) {
        ArrayList<System> systems = new ArrayList<>();
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(QueryGenerator.getInstance().select("system", null,
                    "ProjectID = " + "'" + pid + "'"));
            while (rs.next()) {
                systems.add(fillSystem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return systems;
    }

    public ArrayList<Module> getModules(String sysID) {
        ArrayList<Module> modules = new ArrayList<>();
        ModuleDAO moduleDAO = ModuleDAO.getInstance();
        String query = QueryGenerator.getInstance().select("module_system", null, "SystemID = "
                + "'" + sysID + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                modules.add(moduleDAO.get(rs.getString("ModuleID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    // public static void main(String[] args) {
    // SystemDAO dao = new SystemDAO();
    // //java.lang.System.out.println();
    // java.lang.System.out.println(dao.getModules("1629336"));
    // }

}
