package access;

import java.util.HashMap;
import java.util.Map;

abstract public class AccessLevel {
    private AccessLevelType accessLevelType;
    private Map<PermissionType, Boolean> hasPermission;

    AccessLevel() {
        hasPermission = new HashMap<>();
    }

    public AccessLevelType getAccessLevelType() {
        return accessLevelType;
    }

    public void setAccessLevelType(AccessLevelType accessLevelType) {
        this.accessLevelType = accessLevelType;
    }

    public void setPermission(PermissionType permissionType, Boolean permitStatus) {
        hasPermission.put(permissionType, permitStatus);
    }

    public void setPermissions(Map<PermissionType, Boolean> permits) {
        this.hasPermission = permits;
    }

    public Boolean getPermission(PermissionType permissionType) {
        return hasPermission.get(permissionType);
    }

    public Map<PermissionType, Boolean> getPermissions() {
        return hasPermission;
    }

    @Override
    public String toString() {
        return hasPermission.toString();
    }
}
