package uz.isystem.telegramBot.telegramBot.utill;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.LinkedList;
import java.util.List;

public class MainUtil {
    public static ReplyKeyboardMarkup getMainMarkup(){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);

        KeyboardButton fillProfile = new KeyboardButton();
        fillProfile.setText("Malumot toldirish");

        KeyboardButton infoProfile = new KeyboardButton();
        infoProfile.setText("Malumot korsatish");

        KeyboardRow first = new KeyboardRow();
        first.add(fillProfile);
        KeyboardRow second = new KeyboardRow();
        second.add(infoProfile);

        List<KeyboardRow> keyboardRows = new LinkedList<>();
        keyboardRows.add(first);
        keyboardRows.add(second);

        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public static CurrentMessaga standartmessage(Long chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(String.valueOf(chatId));


        CurrentMessaga currentMessaga = new CurrentMessaga();
        currentMessaga.setSendMessage(sendMessage);
        currentMessaga.setType(MessageType.SEND_MESSGE);
        return currentMessaga;
    }



    public static ReplyKeyboard getPhoneMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        KeyboardRow keyboardRow = new  KeyboardRow();
        List<KeyboardRow> keyboardRowList = new LinkedList<>();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Raqam jo'natish");
        keyboardButton.setRequestContact(true);
        keyboardRow.add(keyboardButton);
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }
}
