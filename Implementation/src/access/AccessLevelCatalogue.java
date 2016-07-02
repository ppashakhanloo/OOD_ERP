package access;

import database.AccessLevelDAO;


public class AccessLevelCatalogue {

    private static AccessLevelCatalogue accessLevelCatalogue;

    private AccessLevelCatalogue() {
    }

    public static AccessLevelCatalogue getInstance() {
        if (accessLevelCatalogue == null)
            accessLevelCatalogue = new AccessLevelCatalogue();
        return accessLevelCatalogue;
    }

    public boolean update(AccessLevel accessLevel) {
        return AccessLevelDAO.getInstance().update(accessLevel);
    }
}
