package kz.basuha.kinobot.dto;

import lombok.Data;

import java.util.List;

@Data
public class Related {

    private List<FullStory> fullStory = null;
    private List<Sequel> sequel = null;

}
