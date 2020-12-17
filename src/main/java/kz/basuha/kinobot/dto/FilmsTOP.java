package kz.basuha.kinobot.dto;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class FilmsTOP {
    private List<Films> films;
    private int pagescount;

    public static class Films {
        private String posterurlpreview;
        private String posterurl;
        private int ratingvotecount;
        private String rating;
        private List<Genres> genres;
        private List<Countries> countries;
        private String filmlength;
        private String year;
        private String nameen;
        private String nameru;
        private int filmid;
    }

    public static class Genres {
        private String genre;
    }

    public static class Countries {
        private String country;
    }

//    public String posterUrlPreview;
//    public String posterUrl;
//    public int ratingVoteCount;
//    public String rating;
//    public List<Genres> genres;
//    public List<Countries> countries;
//    public String filmLength;
//    public String year;
//    public String nameEn;
//    public String nameRu;
//    public int filmId;
//
//
//
//    public static class Genres {
//        public String genre;
//    }
//
//    public static class Countries {
//        public String country;
//    }


}