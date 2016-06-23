package access;

public class AccessLevelFactory {
	public AccessLevel getAccessLevel(String accessLevelID) {

		switch (accessLevelID) {
		case "1":
			return HighAccessLevel.getInstance();
		case "2":
			return MediumAccessLevel.getInstance();
		case "3":
			return LowAccessLevel.getInstance();
		default:
			return null;
		}
	}
}
