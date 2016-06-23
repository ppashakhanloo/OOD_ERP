package project;

public class Technology {
	String name;
	String reason;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Technology(String name, String reason) {
		super();
		this.name = name;
		this.reason = reason;
	}

}
