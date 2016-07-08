package access;

import database.AccessLevelDAO;

public class AccessLevelFactory {

    private AccessLevel accessLevel = null;

    public static void main(String[] args) {
        AccessLevel accessLevel = (new AccessLevelFactory()).getAccessLevel(AccessLevelType.High);
        System.out.println(accessLevel);
    }

    public AccessLevel getAccessLevel(AccessLevelType accessLevelType) {

        switch (accessLevelType) {
            case High:
                accessLevel = HighAccessLevel.getInstance();
                break;
            case Medium:
                accessLevel = MediumAccessLevel.getInstance();
                break;
            case Low:
                accessLevel = LowAccessLevel.getInstance();
                break;
        }
        accessLevel.setPermissions(AccessLevelDAO.getInstance().fillAccessLevel(accessLevelType));
        return accessLevel;
    }
}
