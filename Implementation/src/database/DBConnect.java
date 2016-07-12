package database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

class DBConnect {
    private Connection sqlConn;

    DBConnect(String configFilePath) {
        try {
            HashMap<String, String> connectionProperties = readConfigFile(new File(configFilePath));
            String url = "jdbc:mysql://" + connectionProperties.get("host") + ":" + connectionProperties.get("port") + "/erp?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8";
            String user = connectionProperties.get("user");
            String password = connectionProperties.get("pass");
            sqlConn = DriverManager.getConnection(url, user, password);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap<String, String> readConfigFile(File fin) throws IOException {
        HashMap<String, String> connectionProperties = new HashMap<>(); // host, port, user, pass
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));

        String line;
        while ((line = br.readLine()) != null) {
            String key = line.split("\t")[0];
            String value = "";
            if (line.split("\t").length > 1)
                value = line.split("\t")[1].equals("\"\"") ? "" : line.split("\t")[1];
            connectionProperties.put(key, value);
        }

        br.close();
        return connectionProperties;
    }

    protected Connection getSqlConn() {
        return sqlConn;
    }

}
