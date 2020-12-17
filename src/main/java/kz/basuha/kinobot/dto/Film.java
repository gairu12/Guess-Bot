package kz.basuha.kinobot.dto;

import lombok.Data;

import java.util.List;

@Data
public class Film {

    private Integer filmId;
    private String nameRu;
    private String nameEn;
    private String webUrl;
    private String posterUrl;
    private String posterUrlPreview;
    private String year;
    private String filmLength;
    private Object slogan;
    private Object description;
    private String type;
    private Object ratingMpaa;
    private Object ratingAgeLimits;
    private Object premiereRu;
    private Object distributors;
    private String premiereWorld;
    private Object premiereDigital;
    private String premiereWorldCountry;
    private Object premiereDvd;
    private Object premiereBluRay;
    private Object distributorRelease;
    private List<Country> countries = null;
    private List<Genre> genres = null;
    private List<Object> facts = null;
    private List<Object> seasons = null;


}
