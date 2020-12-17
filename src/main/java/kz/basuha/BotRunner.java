package kz.basuha;

import kz.basuha.kinobot.KinoBot;
import kz.basuha.kinobot.MessageHelper;

public class BotRunner {
    public static void main(String[] args) {
        KinoBot bot = new KinoBot("1439522065:AAEDRR7rNHYBVmi5YA2aaY6kko4isDDTSpo","KinoGuessBot");
        bot.botConnect();
        MessageHelper.init(bot);
    }
}
