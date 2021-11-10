package pdp.uz.service;

import pdp.uz.model.User;

import java.util.UUID;

public interface BaseService<T, R, V> {

    R add(T t);

    T get(UUID uuid);

    R delete(UUID id);


    V check(String str1, String str2);

}
