package pdp.uz.service.category;

import pdp.uz.model.category.ServiceField;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceFieldService implements BaseService<ServiceField> {
    @Override
    public List<ServiceField> getAll(UUID id) {
        return null;
    }

    private final static List<ServiceField> serviceFieldList = new ArrayList<>();
    @Override
    public boolean add(ServiceField serviceField) {
        serviceFieldList.add(serviceField);
        return true;
    }

    @Override
    public ServiceField delete(ServiceField serviceField) {
        return null;
    }

    @Override
    public ServiceField getOne(ServiceField serviceField) {
        return null;
    }

    @Override
    public List<ServiceField> getAll() {
        return null;
    }
}
