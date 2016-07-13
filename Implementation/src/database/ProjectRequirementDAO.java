package database;

import project.Project;
import project.System;
import report.ProjectRequirement;
import resource.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectRequirementDAO extends DBConnect {

    private static ProjectRequirementDAO prjReqDAO;

    private ProjectRequirementDAO() {
        super("erp.conf");
    }

    public static ProjectRequirementDAO getInstance() {
        if (prjReqDAO == null) {
            prjReqDAO = new ProjectRequirementDAO();
        }
        return prjReqDAO;
    }

    public Resource getResource(String key) {
        String query = QueryGenerator.getInstance().select("project_requirement", null, "ID = "
                + "'" + key + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
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
        String query = QueryGenerator.getInstance().update("project_requirement", "ResourceID",
                res.getID(), "ID = " + "'" + key + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Project getProject(String key) {
        String query = QueryGenerator.getInstance().select("project_requirement", null, "ID = "
                + "'" + key + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
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
                       String ResourceID, String moduleID) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String query = "INSERT INTO project_requirement (ID, provideDate, releaseDate,"
                + "isEssential, criticalProvideDate, lengthOfPossession, ProjectID, ResourceID, ModuleID) "
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
                + ", " + "'" + ResourceID + "'"
                + ", " + (moduleID == null ? "NULL" : "'" + moduleID + "'")
                + ");";
        try {
            Statement myStmt = getSqlConn().createStatement();
            myStmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ProjectRequirement get(String key) {
        String query = QueryGenerator.getInstance().select("project_requirement", null, "ID = "
                + "'" + key + "'");
        try {
            Statement myStmt = getSqlConn().createStatement();
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

    public boolean update(ProjectRequirement item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Statement myStmt = getSqlConn().createStatement();
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
                    + (item.getCriticalProvideDate() == null ? "0000-00-00" : sdf
                    .format(item.getCriticalProvideDate()))
                    + "'"
                    + " WHERE ID = " + "'" + item.getID() + "'");
            myStmt.executeUpdate("UPDATE project_requirement SET isEssential  = "
                    + item.isEssential()
                    + " WHERE ID = "
                    + "'"
                    + item.getID()
                    + "'");
            myStmt.executeUpdate("UPDATE project_requirement SET lengthOfPossession  = "
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
            Statement myStmt = getSqlConn().createStatement();
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
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(QueryGenerator.getInstance().select(
                    "project_requirement", null, "ProjectID = " + "'" + pid
                            + "' AND provideDate = " + "'"
                            + "0000-00-00" + "'"));
            while (rs.next()) {
                reqs.add(fillProjectRequirement(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reqs;
    }

    public ArrayList<ProjectRequirement> getAllRequirementByProjectID(String pid) {
        ArrayList<ProjectRequirement> reqs = new ArrayList<>();
        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery(QueryGenerator.getInstance().select(
                    "project_requirement", null, "ProjectID = " + "'" + pid + "'"));
            while (rs.next()) {
                reqs.add(fillProjectRequirement(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reqs;
    }

    public ArrayList<Resource> getModuleResources(String mid) {
        ArrayList<Resource> moduleResources = new ArrayList<>();

        try {
            Statement myStmt = getSqlConn().createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM project_requirement WHERE ModuleID = " + mid);
            while (rs.next()) {
                moduleResources.add(fillProjectRequirement(rs).getResource());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return moduleResources;
    }

    public ArrayList<String> getFlowReport(Date Start, Date End,
                                           List<Resource> resources) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> reports = new ArrayList<>();
        try {
            Statement myStmt = getSqlConn().createStatement();
            for (Resource res : resources) {
                ResultSet rs = myStmt.executeQuery(QueryGenerator.getInstance().select(
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
                                                + (ProjectDAO.getInstance().get(rs.getString("ProjectID")).getName() == null ? "Unnamed"
                                                : ProjectDAO.getInstance().get(rs.getString("ProjectID")).getName())
                                                + " "
                                                + (rs.getDate("provideDate")
                                                .after(Start) ? rs
                                                .getDate("provideDate")
                                                : sdf.format(Start))
                                                + " "
                                                + (rs.getDate("releaseDate") == null ? sdf.format(End)
                                                : (rs.getDate(
                                                "releaseDate")
                                                .before(End) ? rs
                                                .getDate("releaseDate")
                                                : sdf.format(End))));
                                    }
                                } else {
                                    reports.add(res.getID()
                                            + " "
                                            + rs.getString("ProjectID")
                                            + " "
                                            + (ProjectDAO.getInstance().get(rs.getString("ProjectID")).getName() == null ? "Unnamed"
                                            : ProjectDAO.getInstance().get(rs.getString("ProjectID")).getName())
                                            + " "
                                            + (rs.getDate("provideDate"))
                                            + " "
                                            + (rs.getDate("releaseDate") == null ? sdf.format(End)
                                            : (rs.getDate("releaseDate")
                                            .before(End) ? rs
                                            .getDate("releaseDate")
                                            : sdf.format(End))));
                                }
                            }
                        }
                    } else {
                        if (rs.getDate("provideDate") != null) {
                            if (Start != null) {
                                if (rs.getDate("releaseDate") == null
                                        || rs.getDate("releaseDate").after(
                                        Start)) {
                                    reports.add(res.getID()
                                            + " "
                                            + rs.getString("ProjectID")
                                            + " "
                                            + (ProjectDAO.getInstance().get(rs.getString("ProjectID")).getName() == null ? "Unnamed"
                                            : ProjectDAO.getInstance().get(rs.getString("ProjectID")).getName())
                                            + " "
                                            + (rs.getDate("provideDate").after(
                                            Start) ? rs
                                            .getDate("provideDate")
                                            : Start) + " "
                                            + rs.getDate("releaseDate"));
                                }
                            } else {
                                reports.add(res.getID() + " "
                                        + rs.getString("ProjectID") + " "
                                        + (ProjectDAO.getInstance().get(rs.getString("ProjectID")).getName() == null ? "Unnamed"
                                        : ProjectDAO.getInstance().get(rs.getString("ProjectID")).getName())
                                        + " "
                                        + (rs.getDate("provideDate")) + " "
                                        + rs.getDate("releaseDate"));
                            }
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
