package uz.isystem.telegramBot.telegramBot.botApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.isystem.telegramBot.telegramBot.model.User;
import uz.isystem.telegramBot.telegramBot.utill.CurrentMessaga;
import uz.isystem.telegramBot.telegramBot.utill.MainUtil;
import uz.isystem.telegramBot.telegramBot.utill.UserDataCache;

@Service
public class TelegramFacade {
    @Autowired
    private UserDataCache userDataCache;

    private boolean isFilling;


    public  CurrentMessaga handle(Update update) {
        CurrentMessaga returnMessage = new CurrentMessaga();
        if (update.hasMessage()) {
            returnMessage = handleMessage(update.getMessage());
        }
        if (update.hasCallbackQuery()) {
            returnMessage = handleCallBack(update.getCallbackQuery());
        }

        return returnMessage;
    }

    private  CurrentMessaga handleMessage(Message message) {
        String inputText="";
        CurrentMessaga returnMessage=new CurrentMessaga();

        if (message.hasContact()){
            message.setText(message.getContact().getPhoneNumber());
        }

        if (message.hasText()){
            inputText=message.getText();
        }

        if (isFilling){
            returnMessage = fillingProfile(message);
        }

        if (inputText.equals("/start")){
           returnMessage= handleStart(message);
        }

        if (inputText.equals("/help")){
           returnMessage=handleHelp(message);
        }

        if (inputText.equals("Malumot toldirish")){
            returnMessage=handleFill(message);
        }

        if (inputText.equals("Malumot korsatish")){
            returnMessage=handleInfo(message);
        }
        return returnMessage;
    }

    private CurrentMessaga fillingProfile(Message message) {
        long userid = message.getFrom().getId();
        User user = userDataCache.getUserInfo(userid);
         if (user == null){
             user = new User();
             userDataCache.setUserInfo(userid,user);
         }
        if (user.getName() == null){
            user.setUsername(message.getFrom().getUserName());
            user.setName(message.getText());
            userDataCache.setUserInfo(userid,user);
            return MainUtil.standartmessage(message.getChatId(),"Yoshingizni kiriting");
        }

        if (user.getAge() == null && checkAge(message.getText())){
            user.setAge(Integer.valueOf(message.getText()));
            userDataCache.setUserInfo(userid,user);
            return MainUtil.standartmessage(message.getChatId(),"Emailingizni jo'nating");
        }

        if (user.getEmail() == null && checkEmail(message.getText())){
            user.setEmail(message.getText());
            userDataCache.setUserInfo(userid,user);
            CurrentMessaga returnMessage = MainUtil.standartmessage(message.getChatId(),"Raqamingizni yuboring");
            returnMessage.getSendMessage().setReplyMarkup(MainUtil.getPhoneMarkup());
            return returnMessage;
        }

        if (user.getContact() == null){
            user.setContact(message.getText());
            userDataCache.setUserInfo(userid,user);
            isFilling=false;
            CurrentMessaga returnMessage = MainUtil.standartmessage(message.getChatId(),"Malumotlaringizni tekshiring");
            returnMessage.getSendMessage().setReplyMarkup(MainUtil.getMainMarkup());
            return returnMessage;
        }
      return null;
    }

    private boolean checkEmail(String text) {
        String[] list = text.split("@");
        if (list.length != 2) return false;
        String[] newList = list[1].split("\\.");
         return newList.length == 2;
    }

    private boolean checkAge(String age) {
        try {
            Integer.valueOf(age);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private  CurrentMessaga handleInfo(Message message) {
        User user = userDataCache.getUserInfo(message.getFrom().getId());
        if (user == null){
            return MainUtil.standartmessage(message.getChatId(),"Malumot mavjud emas,toldiring");
        }
        String userInfo = String.format("Foydalanuvchi ma'lumotlari:"+
                "\nIsmi - %s,"+
                "\nUsername - @%s,"+
                "\nYoshi - %s,"+
                "\nEmail - %s,"+
                "\nRaqami - %s,",user.getName(),user.getUsername(),user.getAge(),user.getEmail(),user.getContact());
        return MainUtil.standartmessage(message.getChatId(),userInfo);
    }

    private  CurrentMessaga handleFill(Message message) {
        isFilling = true;
        return MainUtil.standartmessage(message.getChatId(), "Ismingizni kiriting");
    }

    private  CurrentMessaga handleHelp(Message message) {
        return MainUtil.standartmessage(message.getChatId(),"Yordam uchun @zufarbeks ga murojat qiling");
    }

    private  CurrentMessaga handleStart(Message message) {
        CurrentMessaga currentMessaga =
                MainUtil.standartmessage(message.getChatId(),"Bosh menyudan buyruq tanlang");
        currentMessaga.getSendMessage().setReplyMarkup(MainUtil.getMainMarkup());
        return currentMessaga;
    }



    private  CurrentMessaga handleCallBack(CallbackQuery callbackQuery) {
        return null;
    }


}
