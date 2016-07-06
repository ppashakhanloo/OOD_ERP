package database;

import project.Project;
import report.ProjectRequirement;
import resource.Resource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectRequirementDAO {

	private Connection sqlConn;
	private String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	private String user = "root";
	private String password = "7284";

	QueryGenerator generator = QueryGenerator.getInstance();

	private static ProjectRequirementDAO prjReqDAO;

	private ProjectRequirementDAO() {
		try {
			sqlConn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ProjectRequirementDAO getInstance() {
		if (prjReqDAO == null) {
			prjReqDAO = new ProjectRequirementDAO();
		}
		return prjReqDAO;
	}

	public Resource getResource(String key) {
		String query = generator.select("project_requirement", null, "ID = "
				+ "'" + key + "'");
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt.executeQuery(query);
			ResourceDAO rsDAO = ResourceDAO.getInstance();
			rs.next();
			return rsDAO.get(rs.getString("ResourceID"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean setResource(String key, Resource res) {
		String query = generator.update("project_requirement", "ResourceID",
				res.getID(), "ID = " + "'" + key + "'");
		try {
			Statement myStmt = sqlConn.createStatement();
			myStmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<ProjectRequirement> getByProvideDate(Date provideDate) {
		ArrayList<ProjectRequirement> projects = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String query = generator.select("project_requirement", null,
				"provideDate = " + "'" + sdf.format(provideDate) + "'");
		ResultSet rs;
		try {
			Statement myStmt = sqlConn.createStatement();
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				projects.add(fillProjectRequirement(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}

	public ArrayList<ProjectRequirement> getByReleaseDate(Date releaseDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<ProjectRequirement> projects = new ArrayList<>();
		String query = generator.select("project_requirement", null,
				"releaseDate = " + "'" + sdf.format(releaseDate) + "'");
		ResultSet rs;
		try {
			Statement myStmt = sqlConn.createStatement();
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				projects.add(fillProjectRequirement(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}

	public ArrayList<ProjectRequirement> getEssentials() {
		ArrayList<ProjectRequirement> projects = new ArrayList<>();
		String query = generator.select("project_requirement", null,
				"isEssential = 1");
		ResultSet rs;
		try {
			Statement myStmt = sqlConn.createStatement();
			rs = myStmt.executeQuery(query);
			while (rs.next()) {
				projects.add(fillProjectRequirement(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}

	public Project getProject(String key) {
		String query = generator.select("project_requirement", null, "ID = "
				+ "'" + key + "'");
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt.executeQuery(query);
			ProjectDAO prjDAO = ProjectDAO.getInstance();
			rs.next();
			return prjDAO.get(rs.getString("ProjectID"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean add(ProjectRequirement item, String ProjectID,
			String ResourceID) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String query = "INSERT INTO project_requirement (ID, provideDate, releaseDate,"
				+ "isEssential, criticalProvideDate, lengthOfPossession, ProjectID, ResourceID) "
				+ "VALUES ('"
				+ item.getID()
				+ "', "
				+ "'"
				+ (item.getProvideDate() == null ? "0000-00-00" : sdf
						.format(item.getProvideDate()))
				+ "'"
				+ ", "
				+ "'"
				+ (item.getReleaseDate() == null ? "0000-00-00" : sdf
						.format(item.getReleaseDate()))
				+ "'"
				+ ", "
				+ "'"
				+ (!item.isEssential() ? "0" : "1")
				+ "'"
				+ ", "
				+ "'"
				+ (item.getCriticalProvideDate() == null ? "0000-00-00" : sdf
						.format(item.getCriticalProvideDate()))
				+ "'"
				+ ", "
				+ item.getLengthOfPossession()
				+ ", "
				+ "'"
				+ ProjectID
				+ "'"
				+ ", " + "'" + ResourceID + "'" + ");";
		try {
			Statement myStmt = sqlConn.createStatement();
			myStmt.executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ProjectRequirement get(String key) {
		String query = generator.select("project_requirement", null, "ID = "
				+ "'" + key + "'");
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt.executeQuery(query);
			if (rs.next()) {
				return (fillProjectRequirement(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ProjectRequirement fillProjectRequirement(ResultSet rs) {
		ProjectRequirement prjReq = null;
		try {
			prjReq = new ProjectRequirement(rs.getString("ID"),
					rs.getDate("provideDate"), rs.getDate("releaseDate"),
					rs.getBoolean("isEssential"),
					rs.getDate("criticalProvideDate"),
					rs.getInt("lengthOfPossession"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prjReq;
	}

	public void remove(String key) {
		String query = generator.delete("project_requirement", "ID = " + "'"
				+ key + "'");
		try {
			Statement myStmt = sqlConn.createStatement();
			myStmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean update(ProjectRequirement item) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Statement myStmt = sqlConn.createStatement();
			myStmt.executeUpdate("UPDATE project_requirement SET provideDate  = "
					+ "'"
					+ (item.getProvideDate() == null ? "0000-00-00" : sdf
							.format(item.getProvideDate()))
					+ "'"
					+ " WHERE ID = " + "'" + item.getID() + "'");
			myStmt.executeUpdate("UPDATE project_requirement SET releaseDate  = "
					+ "'"
					+ (item.getReleaseDate() == null ? "0000-00-00" : sdf
							.format(item.getReleaseDate()))
					+ "'"
					+ " WHERE ID = " + "'" + item.getID() + "'");
			myStmt.executeUpdate("UPDATE project_requirement SET criticalProvideDate  = "
					+ "'"
					+ sdf.format(item.getCriticalProvideDate())
					+ "'"
					+ " WHERE ID = " + "'" + item.getID() + "'");
			myStmt.executeUpdate("UPDATE project_requirement SET isEssential  = "
					+ item.isEssential()
					+ " WHERE ID = "
					+ "'"
					+ item.getID()
					+ "'");
			myStmt.executeUpdate("UPDATE project SET lengthOfPossession  = "
					+ item.getLengthOfPossession() + " WHERE ID = " + "'"
					+ item.getID() + "'");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ArrayList<ProjectRequirement> list() {
		ArrayList<ProjectRequirement> projectReqs = new ArrayList<>();
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt
					.executeQuery("SELECT * FROM project_requirement;");
			while (rs.next()) {
				projectReqs.add(fillProjectRequirement(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projectReqs;
	}

	public ArrayList<ProjectRequirement> getRequirementByProjectID(String pid) {
		ArrayList<ProjectRequirement> reqs = new ArrayList<>();
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt.executeQuery(generator.select(
					"project_requirement", null, "ProjectID = " + "'" + pid
							+ "'"));
			while (rs.next()) {
				reqs.add(fillProjectRequirement(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reqs;
	}

	public ArrayList<Project> getProjectsWithEssentialResource(String rid) {
		ArrayList<Project> projects = new ArrayList<>();
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt.executeQuery(generator.select(
					"project_requirement", null,
					"isEssential = 1 AND ResourceID = " + "'" + rid + "'"));
			while (rs.next()) {
				ProjectDAO dao = ProjectDAO.getInstance();
				projects.add(dao.get(rs.getString("ProjectID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}

	public ArrayList<Resource> getRequiredResources(String pid) {
		ArrayList<Resource> resources = new ArrayList<>();
		try {
			Statement myStmt = sqlConn.createStatement();
			ResultSet rs = myStmt.executeQuery(generator.select(
					"project_requirement", null, "provideDate = '0000-00-00'"
							+ " AND ProjectID = " + "'" + pid + "'"));
			while (rs.next()) {
				ResourceDAO dao = ResourceDAO.getInstance();
				resources.add(dao.get(rs.getString("ResourceID")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resources;
	}

	public ArrayList<String> getFlowReport(Date Start, Date End,
			List<Resource> resources) {
		ArrayList<String> reports = new ArrayList<>();
		try {
			Statement myStmt = sqlConn.createStatement();
			for (Resource res : resources) {
				ResultSet rs = myStmt.executeQuery(generator.select(
						"project_requirement", null, "ResourceID = " + "'"
								+ res.getID() + "'"));
				while (rs.next()) {
					if (End != null) {
						if (rs.getDate("provideDate") != null) {
							if (rs.getDate("provideDate").before(End)) {
								if (Start != null) {
									if (rs.getDate("releaseDate") == null
											|| rs.getDate("releaseDate").after(
													Start)) {
										reports.add(res.getID()
												+ " "
												+ rs.getString("ProjectID")
												+ " "
												+ (rs.getDate("provideDate")
														.after(Start) ? rs
														.getDate("provideDate")
														: Start)
												+ " "
												+ (rs.getDate("releaseDate")
														.before(End) ? rs
														.getDate("releaseDate")
														: End));
									}
								} else {
									reports.add(res.getID()
											+ " "
											+ rs.getString("ProjectID")
											+ " "
											+ (rs.getDate("provideDate"))
											+ " "
											+ (rs.getDate("releaseDate")
													.before(End) ? rs
													.getDate("releaseDate")
													: End));
								}
							}
						}
					} else {
						if (Start != null) {
							if (rs.getDate("releaseDate") == null
									|| rs.getDate("releaseDate").after(Start)) {
								reports.add(res.getID()
										+ " "
										+ rs.getString("ProjectID")
										+ " "
										+ (rs.getDate("provideDate").after(
												Start) ? rs
												.getDate("provideDate") : Start)
										+ " "
										+ rs.getDate("releaseDate"));
							}
						} else {
							reports.add(res.getID()
                                    + " "
									+ rs.getString("ProjectID")
									+ " "
									+ (rs.getDate("provideDate"))
									+ " "
									+ rs.getDate("releaseDate"));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reports;
	}
}
