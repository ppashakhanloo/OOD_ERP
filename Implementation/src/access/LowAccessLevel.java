package access;

import database.AccessLevelDAO;

class LowAccessLevel extends AccessLevel {
	private static LowAccessLevel lowAccessLevel;

	private LowAccessLevel() {
		super();
		setAccessLevelType(AccessLevelType.Low);
		AccessLevelDAO accessLevelDAO = AccessLevelDAO.getInstance();
		accessLevelDAO.fillAccessLevel(AccessLevelType.Low);
	}

	public static LowAccessLevel getInstance() {
		if (lowAccessLevel == null)
			lowAccessLevel = new LowAccessLevel();
		return lowAccessLevel;
	}
}
