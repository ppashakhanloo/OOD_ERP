package resource;

public class Quantity {
	int amount;
	QuantityUnit quantityUnit;

	public Quantity(int amount, QuantityUnit quantityUnit) {
		this.amount = amount;
		this.quantityUnit = quantityUnit;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public QuantityUnit getQuantityUnit() {
		return quantityUnit;
	}

	public void setQuantityUnit(QuantityUnit quantityUnit) {
		this.quantityUnit = quantityUnit;
	}

	@Override
	public String toString() {
		return "[" + Integer.toString(amount) + " " + quantityUnit.toString() + "]";
	}
}
