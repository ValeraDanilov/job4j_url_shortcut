package ru.job4j_url_shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j_url_shortcut.dto.UrlStatisticsDTO;
import ru.job4j_url_shortcut.model.UrlRegistration;
import ru.job4j_url_shortcut.repository.UrlRegistrationRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class SimpleUrlRegistrationService implements UrlRegistrationService {

    private final UrlRegistrationRepository urlRegistrationRepository;

    public UrlRegistration convertUrl(String url) {
        UrlRegistration findUrl = this.urlRegistrationRepository.findByUrl(url);
        if (findUrl != null) {
            return findUrl;
        }
        String key = UUID.randomUUID().toString();
        findUrl = new UrlRegistration(0, key, url, 0);
        this.urlRegistrationRepository.save(findUrl);
        return findUrl;
    }

    public UrlRegistration redirectUrl(String key) {
        return this.urlRegistrationRepository.findByCode(key);
    }

    public List<UrlStatisticsDTO> findAll() {
        return urlRegistrationRepository.findAllUrlAndTotal();
    }

    @Transactional
    public void updateTotal(String code) {
        this.urlRegistrationRepository.update(code);
    }
}
