package database;

import project.Project;
import project.Technology;
import java.util.Date;
import resource.HumanResource;
import unit.Unit;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class ProjectDAO implements DAO<Project> {

    private Connection sqlConn;
    private Statement myStmt;
	private String url = "jdbc:mysql://localhost:9999/erp";
    private String user = "root";
	private String password = "28525336";

    private QueryGenerator generator = QueryGenerator.getInstance();

    private static ProjectDAO projectDAO;

    private ProjectDAO() {
        try {
            sqlConn = DriverManager.getConnection(url, user, password);
            myStmt = sqlConn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ProjectDAO getInstance() {
        if (projectDAO == null) {
            projectDAO = new ProjectDAO();
        }
        return projectDAO;
    }

    public ArrayList<Unit> getUnitsByProjectID(String pid) {
        ArrayList<Unit> units = new ArrayList<>();
        UnitDAO unitDAO = UnitDAO.getInstance();
        String query = generator.select("project_unit", null, "ProjectID = "
                + "'" + pid + "'");
        try {
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                units.add(unitDAO.get(rs.getString("UnitID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }

    public HumanResource getProjectManager(String pid) {
        HumanResourceDAO hrDAO = HumanResourceDAO.getInstance();
        String query = generator.select("project_management", null,
                "ProjectID = " + "'" + pid + "'");
        try {
            ResultSet rs = myStmt.executeQuery(query);
            if (rs.next()) {
                return ((HumanResource) hrDAO.get(rs.getString("ManagerID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean setProjectManager(String pid, String mid) {
        ArrayList<String> colNames = new ArrayList<>();
        colNames.add("ProjectID");
        colNames.add("ManagerID");
        ArrayList<String> values = new ArrayList<>();
        values.add(pid);
        values.add(mid);
        String query = generator.insert("project_management", colNames, values);
        try {
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean addTechnology(Technology tech, String pid) {
        String techExistQuery = generator.select("technology", null, "name = "
                + "'" + tech.getName() + "'");
        try {
            ResultSet rs = myStmt.executeQuery(techExistQuery);
            if (!rs.next()) {
                ArrayList<String> colNames = new ArrayList<>();
                colNames.add("name");
                colNames.add("reason");
                ArrayList<String> values = new ArrayList<>();
                values.add(tech.getName());
                values.add(tech.getReason());
                String addTechQuery = generator.insert("technology", colNames,
                        values);
                myStmt.executeUpdate(addTechQuery);
            }

            ArrayList<String> cols = new ArrayList<>();
            cols.add("ProjectID");
            cols.add("Technologyname");
            ArrayList<String> value = new ArrayList<>();
            value.add(pid);
            value.add(tech.getName());
            String addProjTechQuery = generator.insert("project_technology",
                    cols, value);
            myStmt.executeUpdate(addProjTechQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Technology> getTechnologiesByProject(String pid) {
        ArrayList<Technology> techs = new ArrayList<>();
        String techNamesQuery = generator.select("project_technology", null,
                "ProjectID = " + "'" + pid + "'");
        try {
            ResultSet rs = myStmt.executeQuery(techNamesQuery);
            while (rs.next()) {
                Statement stmt = sqlConn.createStatement();
                ResultSet techRS = stmt
                        .executeQuery(generator.select(
                                "technology",
                                null,
                                "name = " + "'"
                                        + rs.getString("Technologyname") + "'"));
                techRS.first();
                techs.add(fillTechnology(techRS));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return techs;
    }

    private Technology fillTechnology(ResultSet rs) throws SQLException {
        return new Technology(rs.getString("name"),
                rs.getString("reason"));
    }

    @Override
    public boolean add(Project item) {
        return false;
    }

    public boolean add(Project item, ArrayList<String> uid) {
        String query = "INSERT INTO project (ID, name, developmentStart,developmentEnd, customerName, usersCount) VALUES ('"
                + item.getID()
                + "', '"
                + item.getName()
                + "', "
                + item.getDevelopmentStart()
                + ", "
                + item.getDevelopmentEnd()
                + ", '"
                + item.getCustomerName()
                + "', "
                + item.getUsersCount()
                + ");";
        try {
            myStmt.executeUpdate(query);
            for (int i = 0; i < uid.size(); i++) {
                ArrayList<String> colNames = new ArrayList<>();
                colNames.add("UnitID");
                colNames.add("ProjectID");
                ArrayList<String> values = new ArrayList<>();
                values.add(uid.get(i));
                values.add(item.getID());
                String query2 = generator.insert("project_unit", colNames,
                        values);
                myStmt.executeUpdate(query2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Project get(String key) {
        String query = generator.select("project", null, "ID = " + "'" + key
                + "'");
        try {
            ResultSet rs = myStmt.executeQuery(query);
            if (rs.next()) {
                Project newPrj = fillProject(rs);
                return newPrj;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Project fillProject(ResultSet rs) throws SQLException {
        Project project = new Project(rs.getString("ID"), rs.getString("name"),
                rs.getDate("developmentStart"), rs.getDate("developmentEnd"),
                rs.getString("customerName"), rs.getInt("usersCount"));
        return project;
    }

    @Override
    public void remove(String key) {
        String query = generator.delete("project", "ID = " + "'" + key + "'");
        try {
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Project> getByName(String name) {
        ArrayList<Project> projects = new ArrayList<>();
        String query = generator.select("project", null, "name = " + "'" + name
                + "'");
        ResultSet rs;
        try {
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(fillProject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public ArrayList<Project> getByCustomerName(String name) {
        ArrayList<Project> projects = new ArrayList<>();
        String query = generator.select("project", null, "customerName = "
                + "'" + name + "'");
        ResultSet rs;
        try {
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(fillProject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public ArrayList<Project> getByDevelopmentStart(Date DevelopmentStart) {
        ArrayList<Project> projects = new ArrayList<>();
        String query = generator.select("project", null, "developmentStart = "
                + DevelopmentStart);
        ResultSet rs;
        try {
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(fillProject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public ArrayList<Project> getByDevelopmentEnd(Date DevelopmentEnd) {
        ArrayList<Project> projects = new ArrayList<>();
        String query = generator.select("project", null, "developmentEnd = "
                + DevelopmentEnd);
        ResultSet rs;
        try {
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(fillProject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public ArrayList<Project> getByUsersCount(int count) {
        ArrayList<Project> projects = new ArrayList<>();
        String query = generator.select("project", null, "usersCount = "
                + count);
        ResultSet rs;
        try {
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(fillProject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public ArrayList<Project> getByDevelopersCount(int developersCount) {
        return null;
        // TODO
    }

    public ArrayList<Project> getByTechnology(Technology tech) {
        ArrayList<Project> projects = new ArrayList<>();
        String query = generator.select("project_technology", null,
                "Technologyname = " + "'" + tech.getName() + "'");
        ResultSet rs;
        try {
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                projects.add(get(rs.getString("ProjectID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    @Override
    public boolean update(Project item) {
        try {
            myStmt.executeUpdate(generator.update("project", "name",
                    item.getName(), "ID = " + "'" + item.getID() + "'"));
            myStmt.executeUpdate(generator.update("project", "customerName",
                    item.getCustomerName(), "ID = " + "'" + item.getID() + "'"));
            myStmt.executeUpdate("UPDATE project SET developmentStart  = "
                    + item.getDevelopmentStart() + " WHERE ID = " + "'"
                    + item.getID() + "'");
            myStmt.executeUpdate("UPDATE project SET developmentEnd  = "
                    + item.getID() + "'");
            myStmt.executeUpdate("UPDATE project SET usersCount  = "
                    + item.getUsersCount() + " WHERE ID = " + "'"
                    + item.getID() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean updateTechnology(Technology item) {
        try {
            myStmt.executeUpdate(generator.update("technology", "reason",
                    item.getReason(), "name = " + "'" + item.getName() + "'"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public ArrayList<Project> list() {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            ResultSet rs = myStmt.executeQuery("SELECT * FROM project");
            while (rs.next()) {
                projects.add(fillProject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public static void main(String[] args) {
		ProjectDAO prj = ProjectDAO.getInstance();
	       // java.lang.System.out.println(prj.getByName("WEB"));
	        Project proj = new Project("abc123", "prj1", new java.sql.Date(10), new java.sql.Date(10), "customer", 10);
	        ArrayList<String> uniIDs = new ArrayList<String>();
	        uniIDs.add("1");
	        prj.add(proj, uniIDs);
	        java.lang.System.out.println(prj.get("abc123").getCustomerName());
    }

}
