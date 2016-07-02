package access;

import database.AccessLevelDAO;

class MediumAccessLevel extends AccessLevel {
    private static MediumAccessLevel mediumAccessLevel;

    private MediumAccessLevel() {
        super();
        setAccessLevelType(AccessLevelType.Medium);
        AccessLevelDAO accessLevelDAO = AccessLevelDAO.getInstance();
        accessLevelDAO.fillAccessLevel(AccessLevelType.Medium);
    }

    public static MediumAccessLevel getInstance() {
        if (mediumAccessLevel == null)
            mediumAccessLevel = new MediumAccessLevel();
        return mediumAccessLevel;
    }
}
