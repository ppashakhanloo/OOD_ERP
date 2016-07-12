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

    public Map<PermissionType, Boolean> getPermissions() {
        return hasPermission;
    }

    public void setPermissions(Map<PermissionType, Boolean> permits) {
        this.hasPermission = permits;
    }

    @Override
    public String toString() {
        return hasPermission.toString();
    }
}
