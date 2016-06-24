package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import project.ModuleModification;
import resource.HumanResource;

public class ModuleModificationDAO implements DAO<ModuleModification> {

	private Connection sqlConn;
	private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
	private String user = "root";
	private String password = "28525336";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static ModuleModificationDAO moduleModificationDAO;

	private ModuleModificationDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
			myStmt = sqlConn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ModuleModificationDAO getInstance() {
		if (moduleModificationDAO == null) {
			moduleModificationDAO = new ModuleModificationDAO();
		}
		return moduleModificationDAO;
	}

	@Override
	public boolean add(ModuleModification item) {
		return false;
	}

	public boolean add(ModuleModification item, String moduleID) {
		String query = "INSERT INTO module_modification (ID, modificationType, modificationStart,modificationEnd, ModuleID) VALUES ('"
				+ item.getID()
				+ "', '"
				+ item.getModificationType()
				+ "', "
				+ item.getModificationStart()
				+ ", "
				+ item.getModificationEnd() + ", '" + moduleID + "');";
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public boolean addModifier(String modID, HumanResource modifier) {
		ArrayList<String> colNames = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();
		colNames.add("ModuleModificationID");
		colNames.add("HumanResourceID");
		values.add(modID);
		values.add(modifier.getID());
		String query = generator.insert("modulemodification_humanresource",
				colNames, values);
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<HumanResource> getModifiers(String modID) {
		ArrayList<HumanResource> modifiers = new ArrayList<>();
		HumanResourceDAO hrDAO = HumanResourceDAO.getInstance();
		String query = generator.select("modulemodification_humanresource",
				null, "ModuleModificationID = " + modID);
		try {
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				modifiers.add((HumanResource) hrDAO.get(rs
						.getString("HumanResourceID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modifiers;
	}

	@Override
	public ModuleModification get(String key) {
		String query = generator.select("module_modification", null, "ID = "
				+ key);
		try {
			ResultSet rs = myStmt.executeQuery(query);
			if (rs.next()) {
				ModuleModification newMod = fillModuleModification(rs);
				return newMod;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ModuleModification fillModuleModification(ResultSet rs)
			throws SQLException {
		ModuleModification newMod = new ModuleModification(rs.getString("ID"),
				rs.getString("modificationType"),
				rs.getDate("modificationStart"), rs.getDate("modificationEnd"));
		return newMod;
	}

	@Override
	public void remove(String key) {
		String query = "DELETE FROM module_modification WHERE ID = " + key
				+ ";";
		try {
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean update(ModuleModification item) {
		try {
			myStmt.executeUpdate(generator.update("module_modification",
					"modificationType", item.getModificationType(), "ID = "
							+ item.getID()));
			myStmt.executeUpdate("UPDATE module_modification SET modificationStart  = "
					+ item.getModificationStart() + " WHERE ID = "
					+ item.getID());
			myStmt.executeUpdate("UPDATE module_modification SET modificationEnd  = "
					+ item.getModificationEnd() + " WHERE ID = " + item.getID());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ArrayList<ModuleModification> list() {
		ArrayList<ModuleModification> modules = new ArrayList<>();
		try {
			ResultSet rs = myStmt
					.executeQuery("SELECT * FROM module_modification;");
			while (rs.next()) {
				ModuleModification newMod = fillModuleModification(rs);
				modules.add(newMod);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return modules;
	}

	public ArrayList<ModuleModification> getByModuleID(String key) {
		ArrayList<ModuleModification> mods = new ArrayList<>();
		String query = generator.select("module_modification", null,
				"ModuleID = " + key);
		try {
			ResultSet rs = myStmt.executeQuery(query);
			while (rs.next()) {
				mods.add(fillModuleModification(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mods;
	}

}