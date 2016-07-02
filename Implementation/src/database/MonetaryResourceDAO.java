package database;

import resource.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MonetaryResourceDAO extends ResourceDAO {
    private MonetaryResourceDAO() {
        super();
    }

    private static MonetaryResourceDAO monetaryResourceDAO;

    public static MonetaryResourceDAO getInstance() {
        if (monetaryResourceDAO == null)
            monetaryResourceDAO = new MonetaryResourceDAO();
        return monetaryResourceDAO;
    }

    @Override
    public boolean add(Resource item, String projectID) {
        super.add(item, projectID);
        MonetaryResource monetaryResourceItem = (MonetaryResource) item;

        ArrayList<String> colNames = new ArrayList<>();
        colNames.add("monetaryType");
        colNames.add("location");
        colNames.add("accountNumber");
        colNames.add("quantity_amount");
        colNames.add("quantity_unit");
        colNames.add("ResourceID");

        ArrayList<String> values = new ArrayList<>();
        values.add(monetaryResourceItem.getMonetaryType().toString());
        values.add(monetaryResourceItem.getLocation());
        values.add(Integer.toString(monetaryResourceItem.getAccountNumber()));
        values.add(Integer.toString(monetaryResourceItem.getQuantity()
                .getAmount()));
        values.add(monetaryResourceItem.getQuantity().getQuantityUnit()
                .toString());
        values.add(monetaryResourceItem.getID());

        String query = QueryGenerator.getInstance().insert("monetary_resource", colNames,
                values);
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Resource get(String key) {
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt
                    .executeQuery("SELECT * from monetary_resource inner join resource on monetary_resource.ResourceID = resource.ID AND monetary_resource.ResourceID = " + "'" + key + "'");
            Resource newRes = null;
            while (rs.next()) {
                newRes = fillMonetaryResource(rs);
            }
            return newRes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Resource> list() {
        return listConditional(null);
    }

    private ArrayList<Resource> listConditional(String cond) {
        String query = "SELECT * from monetary_resource inner join resource on monetary_resource.ResourceID = resource.ID"
                + (cond == null ? "" : " WHERE " + cond);
        ArrayList<Resource> results = new ArrayList<>();
        ResultSet rs;
        try {
            Statement myStmt = sqlConn.createStatement();
            rs = myStmt.executeQuery(query);
            while (rs.next()) {
                Resource newRes = fillMonetaryResource(rs);
                results.add(newRes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    private Resource fillMonetaryResource(ResultSet rs) throws SQLException {
        MonetaryType monetaryType = MonetaryType.CASH;
        switch (rs.getString("monetaryType")) {
            case "CASH":
                monetaryType = MonetaryType.CASH;
                break;
            case "NON_CASH":
                monetaryType = MonetaryType.NON_CASH;
                break;
        }

        QuantityUnit quantityType = QuantityUnit.RIAL;
        switch (rs.getString("quantity_unit")) {
            case "DOLLAR":
                quantityType = QuantityUnit.DOLLAR;
                break;
            case "RIAL":
                quantityType = QuantityUnit.RIAL;
                break;
        }

        Resource newRes = new MonetaryResource(monetaryType,
                rs.getString("location"), rs.getInt("accountNumber"),
                new Quantity(rs.getInt("quantity_amount"), quantityType));
        newRes.setID(rs.getString("ResourceID"));
        newRes.setAvailable(rs.getString("isAvailable").equals("1"));
        newRes.setResourceStatus(rs.getString("resourceStatus").equals("IDLE") ? ResourceStatus.IDLE
                : ResourceStatus.BUSY);
        return newRes;
    }

    @Override
    public boolean remove(String key) {
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().delete("monetary_resource",
                    "ResourceID = " + key));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return super.remove(key);
    }

    @Override
    public boolean update(Resource item) {
        MonetaryResource monetaryResourceItem = (MonetaryResource) item;
        try {
            Statement myStmt = sqlConn.createStatement();
            myStmt.executeUpdate(QueryGenerator.getInstance().update("monetary_resource",
                    "monetaryType", monetaryResourceItem.getMonetaryType()
                            .toString(),
                    "ResourceID = " + monetaryResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("monetary_resource",
                    "location", monetaryResourceItem.getLocation(),
                    "ResourceID = " + monetaryResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("monetary_resource",
                    "accountNumber",
                    Integer.toString(monetaryResourceItem.getAccountNumber()),
                    "ResourceID = " + monetaryResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("monetary_resource",
                    "quantity_amount", Integer.toString(monetaryResourceItem
                            .getQuantity().getAmount()), "ResourceID = "
                            + monetaryResourceItem.getID()));
            myStmt.executeUpdate(QueryGenerator.getInstance().update("monetary_resource",
                    "quantity_unit", monetaryResourceItem.getQuantity()
                            .getQuantityUnit().toString(), "ResourceID = "
                            + monetaryResourceItem.getID()));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return super.update(item);
    }

    public ArrayList<Resource> getByType(MonetaryType monetaryType) {
        return listConditional("monetaryType = " + "'"
                + monetaryType.toString() + "'");
    }

    public ArrayList<Resource> getByLocation(String location) {
        return listConditional("location = " + "'" + location + "'");
    }

    public ArrayList<Resource> getByQuantity(Quantity quantity) {
        return listConditional("quantity_amount = " + "'"
                + Integer.toString(quantity.getAmount()) + "' AND "
                + "quantity_unit = " + "'"
                + quantity.getQuantityUnit().toString() + "'");
    }

    public ArrayList<Resource> getResourcesByProjectID(String pid) {
        ArrayList<Resource> resources = new ArrayList<>();
        try {
            Statement myStmt = sqlConn.createStatement();
            ResultSet rs = myStmt
                    .executeQuery("SELECT * from monetary_resource inner join resource on monetary_resource.ResourceID = resource.ID AND ProjectID = "
                            + "'" + pid + "'");
            while (rs.next()) {
                resources.add(fillMonetaryResource(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resources;
    }

    public static void main(String[] args) {
        MonetaryResourceDAO dao = new MonetaryResourceDAO();
//        Resource res = new MonetaryResource(MonetaryType.CASH, "Refah",
//                1234567890, new Quantity(100000, QuantityUnit.DOLLAR));
//        System.out.println("ADDED: " + dao.add(res, "1", "1"));
//        MonetaryResource oldRes = (MonetaryResource) dao.get(res.getID());
//        System.out.println("OLD: " + oldRes);
//        System.out.println("ID: " + oldRes.getID());
//        MonetaryResource upRes = new MonetaryResource(oldRes.getMonetaryType(),
//                "Sasan", oldRes.getAccountNumber(), oldRes.getQuantity());
//        upRes.setID(oldRes.getID());
//        System.out.println("UPDATED: " + dao.update(upRes));
//        MonetaryResource newRes = (MonetaryResource) dao.get(res.getID());
//        System.out.println();
//        System.out.println("NEW: " + newRes);
//        System.out.println("REMOVE: " + dao.remove("796940"));
//        System.out.println("LIST: "
//                + dao.getByQuantity(new Quantity(100000, QuantityUnit.DOLLAR)));
    }
}
