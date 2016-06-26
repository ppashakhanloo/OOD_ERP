package access;

import database.AccessLevelDAO;

public class AccessLevelFactory {


    public AccessLevel getAccessLevel(String accessLevelID) {

        AccessLevel accessLevel = null;

        switch (accessLevelID) {
            case "1":
                accessLevel = HighAccessLevel.getInstance();
                break;
            case "2":
                accessLevel = MediumAccessLevel.getInstance();
                break;
            case "3":
                accessLevel = LowAccessLevel.getInstance();
                break;
        }
        accessLevel.setPermissions(AccessLevelDAO.getInstance().fillAccessLevel(accessLevelID));
        return accessLevel;
    }

    public static void main(String[] args) {
        AccessLevel accessLevel = (new AccessLevelFactory()).getAccessLevel("2");
        System.out.println(accessLevel);
    }
}
