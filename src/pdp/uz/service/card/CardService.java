package pdp.uz.service.card;
import pdp.uz.model.Card;
import pdp.uz.model.User;
import pdp.uz.service.BaseService;
import pdp.uz.service.wallet.WalletService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardService implements BaseService<Card, Boolean, Integer> {

    WalletService walletService = new WalletService();

    private final static List<Card> cardList = new ArrayList<>();
    private double cashBackPercent; // 0.5%
    private double commissionPercent; // 1%

    public CardService() {
        Card card = new Card("admin", "561", "561", "561");
        this.cashBackPercent = 0.5; // 0.5%
        this.commissionPercent = 1; // 1%
        cardList.add(card);
    }

    @Override
    public Boolean add(Card card) {
        cardList.add(card);
        return false;
    }

    @Override
    public Card get(UUID uuid) {
        for(int i = 0; i < cardList.size(); ++i){
            if(cardList.get(i).getId().equals(uuid)){
                return cardList.get(i);
            }
        }
        return null;
    }

    public Card getCard(String cardNumber) {
        for(int i = 0; i < cardList.size(); ++i){
            if(cardList.get(i).getCardNumber().equals(cardNumber)){
                return cardList.get(i);
            }
        }
        return null;
    }

    public List<Card> getCardList(String phoneNumber) {
        List<Card> list = new ArrayList<>();

        for (Card card: cardList) {
            if(card.getPhoneNumber().equals(phoneNumber))
                list.add(card);
        }
        return list;
    }

    @Override
    public Boolean delete(UUID uuid) {
        for(int i = 0; i < cardList.size(); ++i){
            if(cardList.get(i).getId().equals(uuid)){
                cardList.remove(i);
                return true;
            }
        }
        return false;
    }

    //##### Check User
    @Override
    public Integer check(String phoneNumber, String password) {

        for (Card card1: cardList) {
            if(card1.getPhoneNumber().equals(phoneNumber)){
                return 1;
            }

        }
        return -1;
    }
    /// ######### Check cardList with its cardNumber
    public static String isExist(String cardNumber){
        for (Card card: cardList) {
            if(card.getCardNumber().equals(cardNumber))
                return card.getOwnerName();
        }
        return null;
    }
    /// ######### (Overloading) Check cardList with its cardNumber also phoneNumber
    public static boolean isExist(String cardNumber, String phoneNumber){
        for (Card card: cardList) {
            if(card.getCardNumber().equals(cardNumber) && card.getPhoneNumber().equals(phoneNumber))
                return true;
        }
        return false;
    }

    public int payToPay(String cardNumber1, String cardNumber2, double transferAmount){

        if(cardNumber1.equals(cardNumber2)) return 0;
        Card card1 = this.getCard(cardNumber1);
        Card card2 = this.getCard(cardNumber2);

        String phoneNumber = card1.getPhoneNumber();

        if(card1.getAmount() >= (1 + this.commissionPercent / 100.0) * transferAmount){

            card1.setAmount(card1.getAmount() - (1 + this.commissionPercent / 100.0) * transferAmount);
            //System.out.println("Card1 amount " +  card1.getAmount() + " | " + (card1.getAmount() - (1 + this.commissionPercent / 100.0) * transferAmount));
            card2.setAmount(card2.getAmount() + transferAmount);
            cardList.get(0).setAmount(cardList.get(0).getAmount() + transferAmount * (this.commissionPercent - this.cashBackPercent) / 100.0);
            updateCardList(cardList.get(0));
            updateCardList(card1);
            updateCardList(card2);

            walletService.addMoney(phoneNumber, transferAmount * this.cashBackPercent / 100.0);

            return 1;
        }

        return -1;
    }

    //Overloading payToPay method
    public int payToPay(String cardNumber1, double transferAmount){

        Card card1 = this.getCard(cardNumber1);
        String phoneNumber = card1.getPhoneNumber();

        if(card1.getAmount() >= (1 + this.commissionPercent / 100.0) * transferAmount){

            card1.setAmount(card1.getAmount() - (1 + this.commissionPercent / 100.0) * transferAmount);
            cardList.get(0).setAmount(cardList.get(0).getAmount() + transferAmount * (this.commissionPercent - this.cashBackPercent) / 100.0);
            updateCardList(cardList.get(0));
            updateCardList(card1);

            walletService.addMoney(phoneNumber, transferAmount * this.cashBackPercent / 100.0);

            return 1;
        }

        return -1;
    }

    private boolean updateCardList(Card card){
        for (int i = 0; i < cardList.size(); ++i){
            if(card.getCardNumber().equals(cardList.get(i))){
                cardList.set(i, card);
                return true;
            }
        }
        return false;
    }

    //Fill User Balance

    public static boolean fillBalance(double amount, String cardNumber){
        if(amount <= 0) return false;

        for(int i = 0; i < cardList.size(); ++i){
            if(cardList.get(i).getCardNumber().equals(cardNumber)){
                cardList.get(i).setAmount(cardList.get(i).getAmount() + amount);
                cardList.set(i, cardList.get(i));
                return true;
            }
        }
        return false;
    }

    public double getCashBackPercent() {
        return cashBackPercent;
    }

    public void setCashBackPercent(double cashBackPercent) {
        this.cashBackPercent = cashBackPercent;
    }

    public double getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(double commissionPercent) {
        this.commissionPercent = commissionPercent;
    }
}
