package database;

import access.PermissionType;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class AccessLevelDAO {
	protected Connection sqlConn;
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "";

	private static AccessLevelDAO accessLevelDAO;
	protected QueryGenerator queryGenerator;

	private AccessLevelDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			queryGenerator = QueryGenerator.getInstance();
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

	public Map<PermissionType, Boolean> fillAccessLevel(String ID) {
		HashMap<PermissionType, Boolean> permitTypes = new HashMap<>();
		ResultSet rs;
		try {
			Statement myStmt = sqlConn.createStatement();
			rs = myStmt.executeQuery(queryGenerator.select("access_level", null, "ID = " + ID));
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
				permitTypes.put(PermissionType.canConfirmNormalUser,
						rs.getString("canConfirmNormalUser").equals("1"));
				permitTypes.put(PermissionType.canGetReport, rs.getString("canGetReport").equals("1"));
				permitTypes.put(PermissionType.canGetResourceAttributes,
						rs.getString("canGetResourceAttributes").equals("1"));
				permitTypes.put(PermissionType.canSearch, rs.getString("canSearch").equals("1"));
//				permitTypes.put(PermissionType.canAddUnit, rs.getString("canAddUnit").equals("1"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return permitTypes;
	}
}
