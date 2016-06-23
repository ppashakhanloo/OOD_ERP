package access;

import database.AccessLevelDAO;

public class HighAccessLevel extends AccessLevel {

	private static HighAccessLevel highAccessLevel;

	private HighAccessLevel() {
		super();
		setID("1");
		AccessLevelDAO accessLevelDAO = AccessLevelDAO.getInstance();
		accessLevelDAO.fillAccessLevel("1");
	}

	public static HighAccessLevel getInstance() {
		if (highAccessLevel == null)
			highAccessLevel = new HighAccessLevel();
		return highAccessLevel;
	}
}
