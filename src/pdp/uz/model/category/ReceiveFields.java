package pdp.uz.model.category;

import java.util.UUID;

public class ReceiveFields {

    private String serviceKey;
    private String serviceValue;
    private String serviceTitle;
    
    private UUID serviceId;
    private UUID uuid;
    private String fieldType;
    
    public ReceiveFields(String serviceKey, String serviceValue, String serviceTitle, UUID serviceId, String fieldType) {
        this.serviceKey = serviceKey;
        this.serviceValue = serviceValue;
        this.serviceTitle = serviceTitle;
        this.serviceId = serviceId;
        this.fieldType = fieldType;
    }

    public ReceiveFields(String serviceKey, String serviceTitle, String fieldType, UUID serviceId) {
        this.serviceKey = serviceKey;
        this.serviceTitle = serviceTitle;
        this.serviceId = serviceId;
        this.fieldType = fieldType;
        this.uuid = UUID.randomUUID();
    }

    public ReceiveFields() {

    }

    public String getServiceName() {
        return serviceTitle;
    }

    public void setServiceTitle(String serviceTitle) {
        this.serviceTitle = serviceTitle;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getServiceTitle() {
        return this.serviceTitle;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getServiceValue() {
        return serviceValue;
    }

    public void setServiceValue(String serviceValue) {
        this.serviceValue = serviceValue;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
