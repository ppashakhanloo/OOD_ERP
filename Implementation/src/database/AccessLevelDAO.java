package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import access.PermissionType;

public class AccessLevelDAO {
	protected Connection sqlConn;
	protected Statement myStmt;
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "0440448182";

	private static AccessLevelDAO accessLevelDAO;
	protected QueryGenerator queryGenerator;

	private AccessLevelDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
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
			rs = myStmt.executeQuery(queryGenerator.select("access_level", null, "ID = " + ID));
			while (rs.next()) {
				permitTypes.put(PermissionType.canAddProject, rs.getString("canAddProject").equals("0") ? false : true);
				permitTypes.put(PermissionType.canAddRemReq, rs.getString("canAddRemReq").equals("0") ? false : true);
				permitTypes.put(PermissionType.canAddRemResource,
						rs.getString("canAddRemResource").equals("0") ? false : true);
				permitTypes.put(PermissionType.canAddRemSysMod,
						rs.getString("canAddRemSysMod").equals("0") ? false : true);
				permitTypes.put(PermissionType.canChangePermission,
						rs.getString("canChangePermission").equals("0") ? false : true);
				permitTypes.put(PermissionType.canConfirmMidUser,
						rs.getString("canConfirmMidUser").equals("0") ? false : true);
				permitTypes.put(PermissionType.canConfirmNormalUser,
						rs.getString("canConfirmNormalUser").equals("0") ? false : true);
				permitTypes.put(PermissionType.canGetReport, rs.getString("canGetReport").equals("0") ? false : true);
				permitTypes.put(PermissionType.canGetResourceAttributes,
						rs.getString("canGetResourceAttributes").equals("0") ? false : true);
				permitTypes.put(PermissionType.canSearch, rs.getString("canSearch").equals("0") ? false : true);
				permitTypes.put(PermissionType.canAddUnit, rs.getString("canAddUnit").equals("0") ? false : true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return permitTypes;
	}
}
