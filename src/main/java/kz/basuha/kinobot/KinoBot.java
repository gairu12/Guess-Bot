package kz.basuha.kinobot;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Getter
@Setter
@RequiredArgsConstructor
public class KinoBot extends TelegramLongPollingBot {

    private static final long RECONNECT_PAUSE = 10000;
    private final String botToken;
    private final String botUsername;
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    }

    static String welcomeMessage =
            "Thank you for using KinoGuessBot" +
                    "Write me if you got problems" +
                    "Developer \uD83D\uDDA4 : https://t.me/ProstoTelega";



    public void onUpdateReceived(Update update) {

        MessageHelper helper = new MessageHelper(update);
        String receivedMessage = helper.receive();
        RequestApi requestApi = new RequestApi();

        if (("/start").equals(receivedMessage)) {
            helper.send("Hi! " + welcomeMessage);
        } else if (("/top").equals(receivedMessage)) {
            requestApi.doRequest();
        }



//        Response response1 = null;
//        try {//TODO: убтрать трай кэтчи в отдельные методы
//            response1 = call1.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ResponseBody body1 = response1.body();
//
//        FilmsTOP filmsTOP = null;
//        try {
//            filmsTOP = objectMapper.readValue(body1.string(), FilmsTOP.class);
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//        //____________________________ //TODO: зачем два клиента? Все вызовы стороннего апи в отдельный класс
//
    }
    public void botConnect(){
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
            System.out.println("TelegramAPI started. Look for messages");
        } catch (TelegramApiException e) {
            System.out.println("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}


