package access;

import database.AccessLevelDAO;

class LowAccessLevel extends AccessLevel {
	private static LowAccessLevel lowAccessLevel;

	private LowAccessLevel() {
		super();
		setID("3");
		AccessLevelDAO accessLevelDAO = AccessLevelDAO.getInstance();
		accessLevelDAO.fillAccessLevel("3");
	}

	public static LowAccessLevel getInstance() {
		if (lowAccessLevel == null)
			lowAccessLevel = new LowAccessLevel();
		return lowAccessLevel;
	}
}
