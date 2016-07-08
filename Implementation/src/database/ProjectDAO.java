package database;

import project.Project;
import project.Technology;
import resource.HumanResource;
import unit.Unit;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProjectDAO implements DAO<Project> {

    private static ProjectDAO projectDAO;
    private Connection sqlConn;
    private String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    private String user = "root";
    private String password = "";
    private QueryGenerator generator = QueryGenerator.getInstance();

    private ProjectDAO() {
        try {
            sqlConn = DriverManager.getConnection(url, user, password);
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

    public static void main(String[] args) {
        ProjectDAO prj = ProjectDAO.getInstance();
        java.lang.System.out.println(prj.getByDevelopersCount(1).toString());
//     Project proj = new Project("abc123", "prj1", null,null, "customer",
//     10);
//     ArrayList<String> uniIDs = new ArrayList<String>();
//     uniIDs.add("1");
//     prj.add(proj, uniIDs);
//     java.lang.System.out.println(prj.get("abc123").getCustomerName());
//     java.lang.System.out.println(prj.getByUsersCount(0).get(0).getID());
//     prj.update(new Project("1", "MIR", null,null,"maryam",4));
//     prj.updateTechnology(new Technology("cj", "want"));
//     java.lang.System.out.println(prj.getUnitsByProjectID("WEB").get(0).getID());
    }

    public ArrayList<Unit> getUnitsByProjectID(String pid) {
        ArrayList<Unit> units = new ArrayList<>();
        UnitDAO unitDAO = UnitDAO.getInstance();
        String query = generator.select("project_unit", null, "ProjectID = "
                + "'" + pid + "'");
        try {
            Statement myStmt = sqlConn.createStatement();
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
            Statement myStmt = sqlConn.createStatement();
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
        // remove old ones
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().delete("project_management", "project_management.ProjectID = " + "'" + pid + "'"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // then insert the new one
        ArrayList<String> colNames = new ArrayList<>();
        colNames.add("ProjectID");
        colNames.add("ManagerID");
        ArrayList<String> values = new ArrayList<>();
        values.add(pid);
        values.add(mid);
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().insert("project_management", colNames, values));
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
            Statement myStmt = sqlConn.createStatement();
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
            Statement myStmt = sqlConn.createStatement();
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
        return new Technology(rs.getString("name"), rs.getString("reason"));
    }

    @Override
    public boolean add(Project item) {
        return false;
    }

   public boolean add(Project item, ArrayList<String> uid) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	String query = "INSERT INTO project (ID, name, developmentStart,developmentEnd, customerName, usersCount) VALUES ('"
                + item.getID()
                + "', '"
                + item.getName()
                + "', "
                + "'"
                + (item.getDevelopmentStart() == null ? "0000-00-00" : sdf
                .format(item.getDevelopmentStart())) + "'"
                + ", "
                + "'"
                + (item.getDevelopmentEnd() == null ? "0000-00-00" : sdf
                .format(item.getDevelopmentEnd())) + "'"
                + ", '"
                + item.getCustomerName()
                + "', "
                + item.getUsersCount()
                + ");";
        try {
            Statement myStmt = sqlConn.createStatement();
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
            Statement myStmt = sqlConn.createStatement();
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
            Statement myStmt = sqlConn.createStatement();
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
            Statement myStmt = sqlConn.createStatement();
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
            Statement myStmt = sqlConn.createStatement();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	ArrayList<Project> projects = new ArrayList<>();
        String query = generator.select("project", null, "developmentStart = "
                + "'"
                     + (DevelopmentStart == null ? "0000-00-00" : sdf
                     .format(DevelopmentStart)) + "'");
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	ArrayList<Project> projects = new ArrayList<>();
        String query = generator.select("project", null, "developmentEnd = "
                + "'"
                     + (DevelopmentEnd == null ? "0000-00-00" : sdf
                     .format(DevelopmentEnd)) + "'");
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
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

        if (count == 0) {
            projects = list();
            return projects;
        }

        String query = generator.select("project", null, "usersCount = "
                + count);
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
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
        ArrayList<Project> allProjects = list();
        ArrayList<Project> projectsWithSameDevelopersCount = new ArrayList<>();

        if (developersCount == 0)
            return allProjects;

        for (Project project : allProjects) {
            String query = "SELECT COUNT(DISTINCT(HumanResourceID)) AS count FROM project INNER JOIN " +
                    "system ON project.ID = system.ProjectID INNER JOIN " +
                    "module_system ON system.ID = module_system.SystemID INNER JOIN " +
                    "module_humanresource ON module_system.ModuleID = module_humanresource.ModuleID " +
                    "WHERE project.ID = " + "'" + project.getID() + "'";

            try {
                Statement myStmt = sqlConn.createStatement();
                ResultSet rs = myStmt.executeQuery(query);
                rs.next();

                if (rs.getInt("count") == developersCount)
                    projectsWithSameDevelopersCount.add(project);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return projectsWithSameDevelopersCount;
    }

    public ArrayList<Project> getByModulesCount(int modulesCount) {
        ArrayList<Project> allProjects = list();
        ArrayList<Project> projectsWithSameModulesCount = new ArrayList<>();

        if (modulesCount == 0)
            return allProjects;

        for (Project project : allProjects) {
            String query = "SELECT COUNT(*) FROM project INNER JOIN system ON project.ID = system.ProjectID INNER JOIN module_system ON system.ID = module_system.SystemID " +
                    "WHERE project.ID = " + "'" + project.getID() + "'";

            try {
                Statement myStmt = sqlConn.createStatement();
                ResultSet rs = myStmt.executeQuery(query);
                rs.next();

                if (rs.getInt("COUNT(*)") == modulesCount)
                    projectsWithSameModulesCount.add(project);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return projectsWithSameModulesCount;
    }

    public ArrayList<Project> getByTechnology(Technology tech) {
        ArrayList<Project> projects = new ArrayList<>();

        if (tech == null) {
            projects = list();
            return projects;
        }

        String query = generator.select("project_technology", null,
                "Technologyname = " + "'" + tech.getName() + "'");
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt.executeQuery(query);
            while (rs.next()) {
                String ID = rs.getString("ProjectID");
                projects.add(get(ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

   @Override
    public boolean update(Project item) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(generator.update("project", "name",
                    item.getName(), "ID = " + "'" + item.getID() + "'"));
            myStmt.executeUpdate(generator.update("project", "customerName",
                    item.getCustomerName(), "ID = " + "'" + item.getID() + "'"));
            myStmt.executeUpdate("UPDATE project SET developmentStart  = "
            		 + "'"
                     + (item.getDevelopmentStart() == null ? "0000-00-00" : sdf
                     .format(item.getDevelopmentStart())) + "'"
                    + " WHERE ID = " + "'"
                    + item.getID() + "'");
            myStmt.executeUpdate("UPDATE project SET developmentEnd  = "
            		 + "'"
                     + (item.getDevelopmentEnd() == null ? "0000-00-00" : sdf
                     .format(item.getDevelopmentEnd())) + "'"
                    +" WHERE ID = " + "'"
                    + item.getID() + "'");
            myStmt.executeUpdate("UPDATE project SET usersCount  = "
                    + item.getUsersCount() 
                    + " WHERE ID = " + "'"
                    + item.getID() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public boolean updateTechnology(Technology item) {
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(generator.update("technology", "reason",
                    item.getReason(), "name = " + "'" + item.getName() + "'"));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public ArrayList<Technology> getAllTechnologies() {
        ArrayList<Technology> technologies = new ArrayList<>();
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM technology");
            while (rs.next()) {
                technologies.add(fillTechnology(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return technologies;
    }

    @Override
    public ArrayList<Project> list() {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt.executeQuery("SELECT * FROM project");
            while (rs.next()) {
                projects.add(fillProject(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

}
