package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import project.Module;
import resource.HumanResource;

public class ModuleDAO implements DAO<Module> {

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static ModuleDAO moduleDAO;

	private ModuleDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
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
		String query = "INSERT INTO module (ID, name, developmentStart,developmentEnd) VALUES ('"
				+ item.getID()
				+ "', '"
				+ item.getName()
				+ "', "
				+ item.getDevelopmentStart()
				+ ", "
				+ item.getDevelopmentEnd()
				+ ");";
		try {
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
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				Module newMod = fillModule(rs);
				return newMod;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Module fillModule(ResultSet rs) throws SQLException {
		Module newMod = new Module(rs.getString("ID"), rs.getString("name"),
				rs.getDate("developmentStart"), rs.getDate("developmentEnd"));
		return newMod;
	}

	@Override
	public void remove(String key) {
		String query = "DELETE FROM module WHERE ID = " + "'" + key + "'" + ";";
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean update(Module item) {
		try {
			myStmt.executeUpdate(generator.update("module", "name",
					item.getName(), "ID = " + "'" + item.getID() + "'"));
			myStmt.executeUpdate("UPDATE module SET developmentStart  = "
					+ item.getDevelopmentStart() + " WHERE ID = " + "'"
					+ item.getID() + "'");
			myStmt.executeUpdate("UPDATE module SET developmentEnd  = "
					+ item.getDevelopmentEnd() + " WHERE ID = " + "'"
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
		ArrayList<Module> modules = new ArrayList<>();
		String query = generator.select("module", null, "developmentStart = "
				+ DevelopmentStart);
		ResultSet rs;
		try {
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
		ArrayList<Module> modules = new ArrayList<>();
		String query = generator.select("module", null, "developmentEnd = "
				+ DevelopmentEnd);
		ResultSet rs;
		try {
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
		String query = generator.insert("module_humanresource", colNames,
				values);
		try {
			myStmt.executeUpdate(query);

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
