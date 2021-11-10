package pdp.uz.service.histroy;

import pdp.uz.model.History;

import java.util.ArrayList;
import java.util.List;

public class HistoryService{
    private final static List<History> historyList = new ArrayList<>();

    public static boolean addHistory(History history){
        historyList.add(history);
        return true;
    }

    public static List<History> getHistoryList(String cardNumber){
        List<History> newList = new ArrayList<>();
        for (History history: historyList) {
            if(history.getFrom().equals(cardNumber))
                newList.add(history);
        }
        return newList;
    }

}
