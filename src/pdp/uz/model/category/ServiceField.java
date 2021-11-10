package pdp.uz.model.category;

import pdp.uz.model.category.BaseCategory;

import java.util.UUID;

public class ServiceField extends BaseCategory {
    private UUID serviceId;
    private String title;

    public ServiceField(String name, UUID serviceId) {
        super(name);
        this.serviceId = serviceId;
    }

    public ServiceField() {

    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
