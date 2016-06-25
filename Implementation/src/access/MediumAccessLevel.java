package access;

import database.AccessLevelDAO;

class MediumAccessLevel extends AccessLevel {
	private static MediumAccessLevel mediumAccessLevel;

	private MediumAccessLevel() {
		super();
		setID("2");
		AccessLevelDAO accessLevelDAO = AccessLevelDAO.getInstance();
		accessLevelDAO.fillAccessLevel("2");
	}

	public static MediumAccessLevel getInstance() {
		if (mediumAccessLevel == null)
			mediumAccessLevel = new MediumAccessLevel();
		return mediumAccessLevel;
	}
}
