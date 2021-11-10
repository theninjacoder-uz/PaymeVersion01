package pdp.uz.service.category;

import pdp.uz.model.category.History;
import pdp.uz.model.category.ReceiveFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HistoryService implements BaseService<History>{

    private static final List<History> fieldHistoryList = new ArrayList<>();

    @Override
    public boolean add(History history) {
        fieldHistoryList.add(history);
        return true;
    }

    @Override
    public History delete(History history) {
        return null;
    }

    @Override
    public History getOne(History history) {
        return null;
    }

    @Override
    public List<History> getAll(UUID id) {
        List<History> list = new ArrayList<>();

        for (History history: fieldHistoryList) {
            if(history.getUserId().equals(id))
                list.add(history);
        }
        return list;
    }

    @Override
    public List<History> getAll() {
        return null;
    }
}
