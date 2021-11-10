package pdp.uz.model;

import java.util.Date;

public class History {
    private String from;
    private String whereTo;
    private double transactionAmount;
    private Date transactionDate;
    private String where;
//    private Re
    public History(String CardNumber, String from, String whereTo, double transactionAmount, String where) {
        this.from = CardNumber;
        this.whereTo = whereTo;
        this.transactionAmount = transactionAmount;
        this.transactionDate = new Date();
        this.where = where;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getWhereTo() {
        return whereTo;
    }
    public void setWhereTo(String whereTo) {
        this.whereTo = whereTo;
    }
    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    public String getWhere() {
        return where;
    }
    public void setWhere(String where) {
        this.where = where;
    }
}
