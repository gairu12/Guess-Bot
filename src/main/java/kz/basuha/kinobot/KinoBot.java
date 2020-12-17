package kz.basuha.kinobot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import kz.basuha.Constants;
import kz.basuha.kinobot.dto.ApiResponseDTO;
import kz.basuha.kinobot.dto.Film;
import kz.basuha.kinobot.dto.FilmsTOP;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;


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

//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(Constants.API_URL + "/v2.1/films/" + receivedMessage)
//                .addHeader(Constants.API_TOKEN_KEY, Constants.API_TOKEN_VALUE)
//                .build();
//        Call call = client.newCall(request);
//
//
//        Response response = null;
//        try {
//            response = call.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ResponseBody body = response.body();
//
//        ApiResponseDTO apiResponseDTO = null;
//        try {
//            apiResponseDTO = objectMapper.readValue(body.string(), ApiResponseDTO.class);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //____________________________

        OkHttpClient client1 = new OkHttpClient();
        Request request1 = new Request.Builder()
                .url(Constants.API_URL + "/v2.2/films/top?type=TOP_250_BEST_FILMS")
                .addHeader(Constants.API_TOKEN_KEY, Constants.API_TOKEN_VALUE)
                .build();
        Call call1 = client1.newCall(request1);

        Response response1 = null;
        try {//TODO: убтрать трай кэтчи в отдельные методы
            response1 = call1.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseBody body1 = response1.body();

        FilmsTOP filmsTOP = null;
        try {
            filmsTOP = objectMapper.readValue(body1.string(), FilmsTOP.class);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        //____________________________ //TODO: зачем два клиента? Все вызовы стороннего апи в отдельный класс
        OkHttpClient client2 = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url(Constants.API_URL + "/v2.1/films/%s/frames")
                .addHeader(Constants.API_TOKEN_KEY, Constants.API_TOKEN_VALUE)
                .build();
        Call call2 = client1.newCall(request1);

        Response response2 = null;
        try {
            response2 = call2.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ResponseBody body2 = response2.body();
        Film film = null;
        try {
            film = objectMapper.readValue(body2.string(),Film.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //____________________________


        if (update.getMessage().getText().equals("/start")) {
            helper.send("Hi! " + welcomeMessage);
        } else if (update.getMessage().getText().equals("/top")) {
            assert filmsTOP != null;
            helper.send(filmsTOP.getFilms().toString());
        } else if (update.getMessage().getText().equals("/guess"))
            helper.send("Type any value amount 200000 please!");
        update.getMessage().getText();
        helper.send(film.getPosterUrlPreview());


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


