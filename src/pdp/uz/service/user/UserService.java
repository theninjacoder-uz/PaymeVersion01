package pdp.uz.service.user;

import pdp.uz.model.User;
import pdp.uz.service.BaseService;

import java.util.*;

public class UserService implements BaseService<User, Boolean, User> {

    private final static List<User> userList = new ArrayList<>();
    private final static Map<String, Integer> map = new HashMap<>();

    public UserService() {
        User user = new User("admin", "561", "root");
        user.setAdmin(true);
        userList.add(user);
    }

    @Override
    public Boolean add(User user) {
        userList.add(user);
        return false;
    }

    @Override
    public User get(UUID uuid) {
        for(int i = 0; i < userList.size(); ++i){
            if(userList.get(i).getId().equals(uuid)){
                return userList.get(i);
            }
        }
        return null;
    }

    @Override
    public Boolean delete(UUID uuid) {

        for(int i = 0; i < userList.size(); ++i){
            if(userList.get(i).getId().equals(uuid)){
                userList.remove(i);
                return true;
            }
        }

        return false;
    }

    @Override
    public User check(String phoneNumber, String password) {

        for (User user: userList) {
            if(user.getPhoneNumber().equals(phoneNumber) && user.getPassword().equals(password)){;
                return user;
            }
        }

        return null;
    }

    public static String isExist(String phoneNumber){
        for (User user: userList) {
            if(user.getPhoneNumber().equals(phoneNumber))
                return user.getName();
        }
        return null;
    }

    public static int sendSmsCode(String phoneNumber){
        int code = (int)(Math.random()*90000+10000);
        map.put(phoneNumber, code);
        return code;
    }
    public static boolean checkCode(int code, String phoneNumber){
        if(map.get(phoneNumber) == code){
            map.remove(phoneNumber);
            return true;
        }
        map.remove(phoneNumber);
        return false;
    }

    public static List<User> getAllUser(){
        return userList;
    }
}
