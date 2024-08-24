package ru.job4j_url_shortcut.service;

import ru.job4j_url_shortcut.dto.UrlStatisticsDTO;
import ru.job4j_url_shortcut.model.UrlRegistration;

import java.util.List;

public interface UrlRegistrationService {

    UrlRegistration convertUrl(String url);

    UrlRegistration redirectUrl(String key);

    List<UrlStatisticsDTO> findAll();

    void updateTotal(UrlRegistration urlRegistration);
}
