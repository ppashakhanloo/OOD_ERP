package resource;

public class MonetaryResource extends Resource {
    private MonetaryType monetaryType;
    private String location;
    private Quantity quantity;
    private int accountNumber;

    public MonetaryResource(String id, MonetaryType monetaryType, String location, Integer accountNumber, Quantity quantity) {
        super();
        setID(id);
        this.monetaryType = monetaryType;
        this.location = location;
        this.accountNumber = accountNumber;
        this.quantity = quantity;
    }

    public MonetaryResource(MonetaryType monetaryType, String location, Integer accountNumber, Quantity quantity) {
        super();
        this.monetaryType = monetaryType;
        this.location = location;
        this.accountNumber = accountNumber;
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

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return super.toString() + ",\n" + "monetaryType=" + monetaryType.toString() + ", location=" + location
                + ", accountNumber=" + Integer.toString(accountNumber) + ", quantity=" + quantity.toString();
    }
}
