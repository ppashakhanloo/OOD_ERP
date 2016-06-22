package resource;

public class MonetaryResource extends Resource {
	MonetaryType monetaryType;
	String location;
	Quantity quantity;

	public MonetaryResource(MonetaryType monetaryType, String location, Quantity quantity) {
		super();
		this.monetaryType = monetaryType;
		this.location = location;
		this.quantity = quantity;
	}

	public MonetaryType getMonetaryType() {
		return monetaryType;
	}

	public void setMonetaryType(MonetaryType monetaryType) {
		this.monetaryType = monetaryType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Quantity getQuantity() {
		return quantity;
	}

	public void setQuantity(Quantity quantity) {
		this.quantity = quantity;
	}

}
