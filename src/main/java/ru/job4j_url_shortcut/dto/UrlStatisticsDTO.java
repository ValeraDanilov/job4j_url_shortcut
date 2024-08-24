package ru.job4j_url_shortcut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlStatisticsDTO {
    private int id;
    private String url;
    private int total;
}
