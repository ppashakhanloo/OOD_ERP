package database;

import unit.Unit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UnitDAO extends DBConnect implements DAO<Unit> {

    private static UnitDAO unitDAO;

    private UnitDAO() {
        super("erp.conf");
    }

    public static UnitDAO getInstance() {
        if (unitDAO == null) {
            unitDAO = new UnitDAO();
        }
        return unitDAO;
    }

    @Override
    public boolean add(Unit item) {
        ArrayList<String> colNames = new ArrayList<>();
        colNames.add("ID");
        colNames.add("name");
        ArrayList<String> values = new ArrayList<>();
        values.add(item.getID());
        values.add(item.getName());
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().insert("unit", colNames, values));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Unit get(String key) {
        String query = QueryGenerator.getInstance()
                .select("unit", null, "ID = " + "'" + key + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            if (rs.next()) {
                return fillUnit(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Unit fillUnit(ResultSet rs) {
        try {
            return (new Unit(rs.getString("ID"), rs.getString("name")));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean update(Unit item) {
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update("unit", "name",
                    item.getName(), "ID = " + "'" + item.getID() + "'"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Unit> list() {
        ArrayList<Unit> units = new ArrayList<>();
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM unit;");
            while (rs.next()) {
                Unit newUnit = fillUnit(rs);
                units.add(newUnit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }
    // public static void main(String[] args) {
    // UnitDAO dao = UnitDAO.getInstance();
    // System.out.println(dao.update(new Unit("1", "test")));
    // }
}
