package resource;

import database.MonetaryResourceDAO;

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

    public String getLocation() {
        return location;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getUnitID() {
        return MonetaryResourceDAO.getInstance().getUnitID(getID());
    }

    @Override
    public String toString() {
//        return super.toString() + ",\n" + "monetaryType=" + monetaryType.toString() + ", location=" + location
//                + ", accountNumber=" + Integer.toString(accountNumber) + ", quantity=" + quantity.toString();
        return "[" + " نوع " + monetaryType + "] " + "[" + " مبلغ " + quantity.toString() + "] " + "[" + location + ", " + accountNumber + "] " + super.toString();
    }
}
