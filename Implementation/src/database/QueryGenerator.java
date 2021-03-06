package database;

import java.util.ArrayList;

public class QueryGenerator {

    private static QueryGenerator queryGenerator;

    private QueryGenerator() {
    }

    public static QueryGenerator getInstance() {
        if (queryGenerator == null)
            queryGenerator = new QueryGenerator();
        return queryGenerator;
    }

    public static void main(String[] args) {
        String z = "ak";
        String a = "no";

        QueryGenerator generator = QueryGenerator.getInstance();
        ArrayList<String> values = new ArrayList<>();
        values.add(z);
        values.add(a);
        ArrayList<String> colNames = new ArrayList<>();
        colNames.add("A");
        colNames.add("B");
        System.out.println(generator.select("student", colNames, null));
        System.out.println(generator.insert("a", colNames, values));
    }

    public String select(String tableName, ArrayList<String> colNames, String cond) {
        return "SELECT " + (colNames == null ? '*' : separatedList(",", "", colNames)) + " FROM " + tableName
                + (cond == null ? "" : " WHERE " + cond);
    }

    public String insert(String tableName, ArrayList<String> colNames, ArrayList<String> values) {
        return "INSERT INTO " + tableName + " (" + separatedList(",", " ", colNames) + ") VALUES ("
                + separatedList(",", "'", values) + ")";
    }

    public String update(String tableName, String colName, String newVal, String cond) {
        return "UPDATE " + tableName + " SET " + colName + " = " + "'" + newVal + "'" + " WHERE " + cond;
    }

    public String delete(String tableName, String cond) {
        return "DELETE FROM " + tableName + " WHERE " + cond;
    }

    private String separatedList(String separator, String wrapper, ArrayList<String> values) {
        if (values.size() == 1)
            return wrapper + values.get(0) + wrapper;
        String stringValues = "";
        for (String value : values) {
            if (value == null)
                stringValues += "NULL" + separator + " ";
            else if (value.equals("NULL"))
                stringValues += value + separator + " ";

            else
                stringValues += wrapper + value + wrapper + separator + " ";
        }
        return stringValues.substring(0, stringValues.length() - 2);
    }
}
