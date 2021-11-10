package pdp.uz.model;

public class User extends BaseModel {
    private String password;
    private boolean isAdmin;
    private int smsCode;

    public User(String name, String phoneNumber, String password) {
        super(name, phoneNumber);

        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(int smsCode) {
        this.smsCode = smsCode;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }


}
