package access;

import java.util.HashMap;
import java.util.Map;

abstract public class AccessLevel {
	private String ID;
	private Map<PermissionType, Boolean> hasPermission;

	public AccessLevel() {
		hasPermission = new HashMap<>();
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
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

	@Override
	public String toString() {
		return hasPermission.toString();
	}
}
