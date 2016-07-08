package database;

import access.AccessLevel;
import access.AccessLevelType;
import access.PermissionType;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AccessLevelDAO {
    private static AccessLevelDAO accessLevelDAO;
    protected Connection sqlConn;
    private String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "7284";

    private AccessLevelDAO() {
        try {
            sqlConn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static AccessLevelDAO getInstance() {
        if (accessLevelDAO == null) {
            accessLevelDAO = new AccessLevelDAO();
        }
        return accessLevelDAO;
    }

    public boolean update(AccessLevel item) {
        AccessLevel accessLevel = item;
        Map<PermissionType, Boolean> permissions = accessLevel.getPermissions();

        try {
            Statement myStmt = sqlConn.createStatement();
            for (PermissionType permission : permissions.keySet())
                myStmt.executeUpdate(QueryGenerator.getInstance().update("access_level",
                        permission.toString(), (permissions.get(permission).booleanValue() ? "1" : "0"), "accessLevelType = "
                                + "'" + accessLevel.getAccessLevelType().toString() + "'"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Map<PermissionType, Boolean> fillAccessLevel(AccessLevelType accessLevelType) {
        HashMap<PermissionType, Boolean> permitTypes = new HashMap<>();
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
            rs = myStmt.executeQuery(QueryGenerator.getInstance().select("access_level", null, "accessLevelType = " + "'" + accessLevelType.toString()) + "'");
            while (rs.next()) {
                permitTypes.put(PermissionType.canAddProject, rs.getString("canAddProject").equals("1"));
                permitTypes.put(PermissionType.canAddRemReq, rs.getString("canAddRemReq").equals("1"));
                permitTypes.put(PermissionType.canAddRemResource,
                        rs.getString("canAddRemResource").equals("1"));
                permitTypes.put(PermissionType.canAddRemSysMod,
                        rs.getString("canAddRemSysMod").equals("1"));
                permitTypes.put(PermissionType.canChangePermission,
                        rs.getString("canChangePermission").equals("1"));
                permitTypes.put(PermissionType.canConfirmMidUser,
                        rs.getString("canConfirmMidUser").equals("1"));
                permitTypes.put(PermissionType.canConfirmLowUser,
                        rs.getString("canConfirmLowUser").equals("1"));
                permitTypes.put(PermissionType.canConfirmHighUser,
                        rs.getString("canConfirmHighUser").equals("1"));
                permitTypes.put(PermissionType.canGetReport, rs.getString("canGetReport").equals("1"));
                permitTypes.put(PermissionType.canGetResourceAttributes,
                        rs.getString("canGetResourceAttributes").equals("1"));
                permitTypes.put(PermissionType.canSearch, rs.getString("canSearch").equals("1"));
                permitTypes.put(PermissionType.canAddUnit, rs.getString("canAddUnit").equals("1"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return permitTypes;
    }
}
