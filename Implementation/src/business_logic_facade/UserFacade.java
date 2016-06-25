package business_logic_facade;

import access.PermissionType;
import resource.HumanResource;
import resource.ResourceCatalogue;

import java.util.Map;

/**
 * Created by ppash on 6/24/2016.
 */
public class UserFacade {
    private HumanResource currentUser;

    public boolean login(String ID, String password) {
        currentUser = ResourceCatalogue.getInstance().humanResourceLogin(ID, password);
        System.out.println("CURRENT USER ACCESS LEVEL ID = " + currentUser.getAccessLevel().getID());
        System.out.println("CURRENT USER ID = " + currentUser.getID());
        return currentUser != null;
    }

    public void logout(String ID) {
        ResourceCatalogue.getInstance().humanResourceLogout(ID);
    }

    public String getID() {
        return currentUser.getID();
    }


    public Map<PermissionType, Boolean> getCurrentUserPermissions() {
        return currentUser.getAccessLevel().getPermissions();
    }
}
