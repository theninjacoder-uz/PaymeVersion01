package pdp.uz.model;

public class Wallet extends BaseModel {
    private double walletAmount;
    private String phoneNumber;

    public Wallet(String phoneNumber) {
        super();
        this.walletAmount = 0;
        this.phoneNumber = phoneNumber;
    }

    public Wallet(String phoneNumber, double walletAmount) {
        super();
        this.walletAmount = walletAmount;
        this.phoneNumber = phoneNumber;
    }

    public double getWalletAmount() {
        return walletAmount;
    }

    public Wallet setWalletAmount(double walletAmount) {
        this.walletAmount = walletAmount;
        return null;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
