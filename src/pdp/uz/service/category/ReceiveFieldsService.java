package pdp.uz.service.category;

import pdp.uz.model.category.ReceiveFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReceiveFieldsService {
    private final static List<ReceiveFields> receiveFieldsList = new ArrayList<>();

    public static void addKey(ReceiveFields receiveFields){
        receiveFieldsList.add(receiveFields);
    }

    public static List<ReceiveFields> getReceiveFieldsList(UUID serviceId){
        List<ReceiveFields> list = new ArrayList<>();
        for (ReceiveFields receiveFields:receiveFieldsList) {
            if(receiveFields.getServiceId().equals(serviceId))
                list.add(receiveFields);
        }
        return list;
    }

    public static void updateReceiveFieldsList(ReceiveFields receiveFields){
        int ind = 0;
        for (ReceiveFields receiveFields1:receiveFieldsList) {
            if(receiveFields1.getUuid().equals(receiveFields.getUuid()))
                receiveFieldsList.set(ind, receiveFields);
            ind++;
        }
    }
}
