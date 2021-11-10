package pdp.uz.model.category;

import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.UUID;

public class History {
    private String serviceName;
    private UUID serviceId;
    private UUID userId;
    private ArrayList<String> fieldKey = new ArrayList<>();
    private ArrayList<String> fieldValue = new ArrayList<>();
    private String cardNumber;

    public History(String serviceName, UUID userId, List<String> fieldKey1, List<String> fieldValue1, String cardNumber) {
        this.serviceName = serviceName;
        this.cardNumber = cardNumber;
        fieldKey.addAll(fieldKey1);
        fieldValue.addAll(fieldValue1);
        this.userId = userId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    //    public String getFieldKey() {
//        return fieldKey;
//    }
//
//    public void setFieldKey(String fieldKey) {
//        this.fieldKey = fieldKey;
//    }
//
//    public String getFieldValue() {
//        return fieldValue;
//    }
//
//    public void setFieldValue(String fieldValue) {
//        this.fieldValue = fieldValue;
//    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public List<String> getFieldKey() {
        return fieldKey;
    }


    public List<String> getFieldValue() {
        return fieldValue;
    }

}
