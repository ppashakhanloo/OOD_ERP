package business_logic_facade;

import access.*;
import resource.HumanResource;
import resource.ResourceCatalogue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserFacade {
    private HumanResource currentUser;

    public boolean login(String ID, String password) {
        currentUser = ResourceCatalogue.getInstance().humanResourceLogin(ID, password);
        System.out.println("CURRENT USER ACCESS LEVEL ID = " + currentUser.getAccessLevel().getAccessLevelType());
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

    public ArrayList<AccessLevelType> getAllAccessLevelTypes() {
        ArrayList<AccessLevelType> accessLevelTypes = new ArrayList<>();
        Collections.addAll(accessLevelTypes, AccessLevelType.values());
        return accessLevelTypes;
    }

    public Map<PermissionType, Boolean> getPermissions(AccessLevelType accessLevelType) {
        return new AccessLevelFactory().getAccessLevel(accessLevelType).getPermissions();
    }

    public void setPermissions(AccessLevel accessLevel) {

    }

    public void setPermissions(AccessLevelType accessLevelType, HashMap<PermissionType, Boolean> permissions) {
        AccessLevelFactory accessLevelFactory = new AccessLevelFactory();
        AccessLevel accessLevel = accessLevelFactory.getAccessLevel(accessLevelType);
        accessLevel.setPermissions(permissions);
        AccessLevelCatalogue.getInstance().update(accessLevel);
    }
}
