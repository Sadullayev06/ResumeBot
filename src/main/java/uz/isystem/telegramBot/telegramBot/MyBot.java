package uz.isystem.telegramBot.telegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.isystem.telegramBot.telegramBot.botApi.TelegramFacade;
import uz.isystem.telegramBot.telegramBot.utill.CurrentMessaga;
import uz.isystem.telegramBot.telegramBot.utill.MessageType;


@Component
public class MyBot extends TelegramLongPollingBot {

    @Autowired
    private TelegramFacade telegramFacade;


    @Override
    public String getBotUsername() {
        return "Resume_Bot";
    }

    @Override
    public String getBotToken() {
        return "5361027170:AAEMZlN9tPCewRQYpNNH4q92N90sJn-Z5OM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        CurrentMessaga messaga = telegramFacade.handle(update);
        if (messaga != null && messaga.getType() != null) {
            executeMessage(messaga);
        }
    }


    private void executeMessage(CurrentMessaga messaga) {
        MessageType type = messaga.getType();
        try {
            if (type.equals(MessageType.SEND_PHOTO)) {
                execute(messaga.getSendPhoto());
            }
            if (type.equals(MessageType.SEND_MESSGE)) {
                execute(messaga.getSendMessage());
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
