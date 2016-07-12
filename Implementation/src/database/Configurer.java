package database;


import java.lang.reflect.Field;

public class Configurer {
    private String url = "jdbc:mysql://localhost:3306/erp?zeroDateTimeBehavior=convertToNull&useUnicode=true&characterEncoding=UTF-8";
    private String user = "root";
    private String password = "";

    public Configurer(String url, String user, String password) throws IllegalAccessException, NoSuchFieldException {
        AccessLevelDAO accessLevelDAO = AccessLevelDAO.getInstance();
        Field urlStringField = accessLevelDAO.getClass().getDeclaredField("url");
        urlStringField.setAccessible(true);
        String fieldValue = (String) urlStringField.get(accessLevelDAO);
        System.out.println("fieldValue = " + fieldValue);
        urlStringField.setAccessible(false);
    }

    public static void main(String[] args) {
        try {
            Configurer configurer = new Configurer("a", "b", "c");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
