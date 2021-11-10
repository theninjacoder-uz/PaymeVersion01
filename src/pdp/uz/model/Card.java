package pdp.uz.model;

public class Card extends BaseModel {
    private String cardNumber;
    private String expirationDate;
    private double amount;
    private String ownerName;


    public Card(String name, String phoneNumber, String cardNumber, String expirationDate) {
        super(name, phoneNumber);
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.amount = 0;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
