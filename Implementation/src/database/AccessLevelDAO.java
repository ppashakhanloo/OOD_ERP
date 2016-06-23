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
		try {
			ResultSet rs = myStmt.executeQuery(queryGenerator.select("access_level", null, "ID = " + ID));
			permitTypes.put(PermissionType.canGetReport, rs.getInt("canGetReport") == 0 ? false : true);
			permitTypes.put(PermissionType.canSearch, rs.getInt("canSearch") == 0 ? false : true);
			permitTypes.put(PermissionType.canGetResourceAttributes,
					rs.getInt("canGetResourceAttributes") == 0 ? false : true);
			permitTypes.put(PermissionType.canAddRemResource, rs.getInt("canAddRemResource") == 0 ? false : true);
			permitTypes.put(PermissionType.canAddRemReq, rs.getInt("canAddRemReq") == 0 ? false : true);
			permitTypes.put(PermissionType.canAddProject, rs.getInt("canAddProject") == 0 ? false : true);
			permitTypes.put(PermissionType.canAddRemSysMod, rs.getInt("canAddRemSysMod") == 0 ? false : true);
			permitTypes.put(PermissionType.canChangePermission, rs.getInt("canChangePermission") == 0 ? false : true);
			permitTypes.put(PermissionType.canConfirmNormalUser, rs.getInt("canConfirmNormalUser") == 0 ? false : true);
			permitTypes.put(PermissionType.canConfirmMidUser, rs.getInt("canConfirmMidUser") == 0 ? false : true);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return permitTypes;
	}
}
