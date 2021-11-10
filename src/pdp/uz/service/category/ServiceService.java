package pdp.uz.service.category;

import pdp.uz.model.category.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServiceService implements BaseService<Service>{
    private final static List<Service> serviceList = new ArrayList<>();

    @Override
    public boolean add(Service service) {
        serviceList.add(service);
        return true;
    }

    @Override
    public Service delete(Service service) {
        return null;
    }

    @Override
    public Service getOne(Service service) {
        return null;
    }

    @Override
    public List<Service> getAll(UUID categoryId) {
        List<Service> list = new ArrayList<>();

        for (Service service: serviceList) {
            if(service.getCategoryId().equals(categoryId))
                list.add(service);
        }

        return list;
    }

    @Override
    public List<Service> getAll() {
        return null;
    }
}
