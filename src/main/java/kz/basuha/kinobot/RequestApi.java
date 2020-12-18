package kz.basuha.kinobot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import kz.basuha.Constants;
import kz.basuha.kinobot.dto.Film;
import kz.basuha.kinobot.dto.FilmsTOP;

import java.io.IOException;

public class RequestApi {
    private final OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = null;

    public void doRequest(String url) throws JsonProcessingException {
        String filmFramesUrl = "/v2.1/films/%s/frames";
        String top250Url = "/v2.2/films/top?type=TOP_250_BEST_FILMS";
        String filmInfoUrl = "/v2.1/films/%s";

        String top = String.format(top250Url);
        String filmFrames = String.format(filmFramesUrl, 2314);
        String infoFilm = String.format(filmInfoUrl, 6342);


        Request request = new Request.Builder()
                .url(Constants.API_URL + url)
                .addHeader(Constants.API_TOKEN_KEY, Constants.API_TOKEN_VALUE)
                .build();

        Call call = client.newCall(request);
        String q = responseEx(call);

        Film film = objectMapper.readValue(q, Film.class);
        film.getNameRu();

        Film frames = objectMapper.readValue(q, Film.class);
        frames.getPosterUrlPreview();

        FilmsTOP top = objectMapper.readValue(q, FilmsTOP.class);
        top.getFilms();



    }

    private String responseEx(Call call) {
        String string = "";
        try {
            Response response = call.execute();
            string = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

}
