package kz.basuha.kinobot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageHelper {

    private static KinoBot bot;

    private final long chatId;

    private Update update;

    public MessageHelper(Update update) {
        this.update = update;
        this.chatId = update.getMessage().getChatId();
    }

    public static void init(KinoBot bot) {
        MessageHelper.bot = bot;
    }

    public String receive() {
        return update.getMessage().getText();
    }

    public void send(String message) {
        SendMessage sendMessageAction = new SendMessage(String.valueOf(chatId), message);
        try {
            bot.execute(sendMessageAction);
        } catch (TelegramApiException e) {
            System.out.println("Error sending message to " + chatId);
        }
    }
}
