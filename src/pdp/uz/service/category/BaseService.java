package pdp.uz.service.category;

import java.util.List;
import java.util.UUID;

public interface BaseService<T> {
    boolean add(T t);
    T delete(T t);
    T getOne(T t);
    List<T> getAll(UUID id);
    List<T> getAll();
}
