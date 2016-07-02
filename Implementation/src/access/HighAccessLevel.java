package access;

import database.AccessLevelDAO;

class HighAccessLevel extends AccessLevel {

    private static HighAccessLevel highAccessLevel;

    private HighAccessLevel() {
        super();
        setAccessLevelType(AccessLevelType.High);
        AccessLevelDAO accessLevelDAO = AccessLevelDAO.getInstance();
        accessLevelDAO.fillAccessLevel(AccessLevelType.High);
    }

    public static HighAccessLevel getInstance() {
        if (highAccessLevel == null)
            highAccessLevel = new HighAccessLevel();
        return highAccessLevel;
    }
}
