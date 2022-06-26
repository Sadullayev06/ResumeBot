package uz.isystem.telegramBot.telegramBot.utill;

import org.springframework.stereotype.Component;
import uz.isystem.telegramBot.telegramBot.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache {

    private final Map<Long, User> userMap;

    public UserDataCache(){
        this.userMap=new HashMap<>();
    }

    public void setUserInfo(Long userId,User user){
            userMap.put(userId,user);
    }

    public User getUserInfo(Long userId){
        return userMap.get(userId);
    }

}
