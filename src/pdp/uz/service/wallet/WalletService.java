package pdp.uz.service.wallet;

import pdp.uz.model.User;
import pdp.uz.model.Wallet;
import pdp.uz.service.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WalletService implements BaseService<Wallet, Boolean, Integer> {
    private final static List<Wallet> walletsList = new ArrayList<>();

    @Override
    public Boolean add(Wallet wallet) {
        walletsList.add(wallet);
        return true;
    }

    @Override
    public Wallet get(UUID uuid) {

        return null;
    }

    public static double getWallet(String phoneNumber){
        for (Wallet wallet: walletsList) {
            if(wallet.getPhoneNumber().equals(phoneNumber))
                return wallet.getWalletAmount();

        }
        return 0;
    }

    public boolean addMoney(String phoneNumber, double amount){
        for (int i = 0; i < walletsList.size(); ++i) {
            if(walletsList.get(i).getPhoneNumber().equals(phoneNumber)){
                double walletAmount = walletsList.get(i).getWalletAmount() + amount;
                Wallet wallet = new Wallet(phoneNumber, walletAmount);
                walletsList.set(i, wallet);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean delete(UUID id) {
        return false;
    }

    @Override
    public Integer check(String str1, String str2) {
        return -1;
    }
}
