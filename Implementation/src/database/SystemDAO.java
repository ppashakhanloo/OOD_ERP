package database;

import project.Module;
import project.System;

import java.sql.*;
import java.util.ArrayList;

public class SystemDAO implements DAO<System> {

    private static SystemDAO systemDAO;
    QueryGenerator generator = QueryGenerator.getInstance();
    private Connection sqlConn;
    private String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "";

    private SystemDAO() {
        try {
            sqlConn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String query = generator.insert("system", cols, values);
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public System get(String key) {
        String query = generator.select("system", null, "ID = " + "'" + key
                + "'");
        try {
            Statement myStmt = sqlConn.createStatement();
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
    public void remove(String key) {
        String query = "DELETE FROM system WHERE ID = " + "'" + key + "'" + ";";
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(System item) {
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(generator.update("system", "name",
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
            Statement myStmt = sqlConn.createStatement();
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
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt.executeQuery(generator.select("system", null,
                    "ProjectID = " + "'" + pid + "'"));
            while (rs.next()) {
                systems.add(fillSystem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return systems;
    }

    public ArrayList<System> getByName(String name) {
        ArrayList<System> systems = new ArrayList<>();
        String query = generator.select("system", null, "name = " + "'" + name
                + "'");
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt.executeQuery(query);
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
        String query = generator.select("module_system", null, "SystemID = "
                + "'" + sysID + "'");
        try {
            Statement myStmt = sqlConn.createStatement();
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
