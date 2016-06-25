package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import project.Project;
import report.ProjectRequirement;
import resource.Resource;

public class ProjectRequirementDAO implements DAO<ProjectRequirement> {
    private Connection sqlConn;
    private Statement myStmt;
    private String url = "jdbc:mysql://localhost:3306/erp";
    private String user = "root";
    private String password = "0440448182";

    QueryGenerator generator = QueryGenerator.getInstance();

    private static ProjectRequirementDAO prjReqDAO;

    private ProjectRequirementDAO() {
        try {
            sqlConn = DriverManager.getConnection(url, user, password);
            myStmt = sqlConn.createStatement();
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
            ResultSet rs = myStmt.executeQuery(query);
            ResourceDAO rsDAO = ResourceDAO.getInstance();
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
            myStmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<ProjectRequirement> getByProvideDate(Date provideDate) {
        ArrayList<ProjectRequirement> projects = new ArrayList<>();
        String query = generator.select("project_requirement", null,
                "provideDate = " + provideDate);
        ResultSet rs;
        try {
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(fillProjReq(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public ArrayList<ProjectRequirement> getByReleaseDate(Date releaseDate) {
        ArrayList<ProjectRequirement> projects = new ArrayList<>();
        String query = generator.select("project_requirement", null,
                "releaseDate = " + releaseDate);
        ResultSet rs;
        try {
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(fillProjReq(rs));
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
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(fillProjReq(rs));
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
            ResultSet rs = myStmt.executeQuery(query);
            ProjectDAO prjDAO = ProjectDAO.getInstance();
            return prjDAO.get(rs.getString("ProjectID"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean add(ProjectRequirement item) {
        return false;
    }

    public boolean add(ProjectRequirement item, String ProjectID,
                       String ResourceID) {
        String query = "INSERT INTO project_requirement (ID, provideDate, releaseDate,"
                + "isEssential, criticalProvideDate, lengthOfPossession, ProjectID, ResourceID) "
                + "VALUES ('"
                + item.getID()
                + "', "
                + item.getProvideDate()
                + ", "
                + item.getReleaseDate()
                + ", "
                + item.isEssential()
                + ", "
                + item.getCriticalProvideDate()
                + ", "
                + item.getLengthOfPossession()
                + ", '"
                + ProjectID
                + "', '"
                + ResourceID + "');";
        try {
            myStmt.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ProjectRequirement get(String key) {
        String query = generator.select("project_requirement", null, "ID = "
                + "'" + key + "'");
        try {
            ResultSet rs = myStmt.executeQuery(query);
            if (rs.next()) {
                return (fillProjReq(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected ProjectRequirement fillProjReq(ResultSet rs) {
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

    @Override
    public void remove(String key) {
        String query = generator.delete("project_requirement", "ID = " + "'"
                + key + "'");
        try {
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(ProjectRequirement item) {
        try {
            myStmt.executeUpdate("UPDATE project SET provideDate  = "
                    + item.getProvideDate() + " WHERE ID = " + "'"
                    + item.getID() + "'");
            myStmt.executeUpdate("UPDATE project SET releaseDate  = "
                    + item.getReleaseDate() + " WHERE ID = " + "'"
                    + item.getID() + "'");
            myStmt.executeUpdate("UPDATE project SET criticalProvideDate  = "
                    + item.getCriticalProvideDate() + " WHERE ID = " + "'"
                    + item.getID() + "'");
            myStmt.executeUpdate("UPDATE project SET isEssential  = "
                    + item.isEssential() + " WHERE ID = " + "'" + item.getID()
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

    @Override
    public ArrayList<ProjectRequirement> list() {
        ArrayList<ProjectRequirement> projectReqs = new ArrayList<>();
        try {
            ResultSet rs = myStmt
                    .executeQuery("SELECT * FROM project_requirement;");
            while (rs.next()) {
                projectReqs.add(fillProjReq(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectReqs;
    }

    public ArrayList<ProjectRequirement> getRequirementByProjectID(String pid) {
        ArrayList<ProjectRequirement> reqs = new ArrayList<>();
        try {
            ResultSet rs = myStmt.executeQuery(generator.select(
                    "project_requirement", null, "ProjectID = " + "'" + pid
                            + "'"));
            while (rs.next()) {
                reqs.add(fillProjReq(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reqs;
    }

    public ArrayList<Project> getProjectsWithEssentialResource(String rid) {
        ArrayList<Project> Reqs = new ArrayList<>();
        try {
            ResultSet rs = myStmt.executeQuery(generator.select(
                    "project_requirement", null,
                    "isEssential = 1 AND ResourceID = " + "'" + rid + "'"));
            while (rs.next()) {
                ProjectDAO dao = ProjectDAO.getInstance();
                Reqs.add(dao.get(rs.getString("ProjectID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Reqs;
    }

    public ArrayList<Resource> getRequiredResources(String pid) {
        ArrayList<Resource> Ress = new ArrayList<>();
        try {
            ResultSet rs = myStmt.executeQuery(generator.select(
                    "project_requirement", null, "provideDate = ?"
                            + " AND ProjectID = " + "'" + pid + "'"));
            while (rs.next()) {
                ResourceDAO dao = ResourceDAO.getInstance();
                Ress.add(dao.get(rs.getString("ResourceID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Ress;
    }
}
