package uz.isystem.telegramBot.telegramBot.utill;

import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

@Getter
@Setter

public class CurrentMessaga {

    private SendMessage sendMessage;
    private SendPhoto sendPhoto;

    private MessageType type;
}
