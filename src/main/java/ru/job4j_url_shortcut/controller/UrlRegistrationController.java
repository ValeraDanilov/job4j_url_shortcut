package ru.job4j_url_shortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j_url_shortcut.dto.UrlStatisticsDTO;
import ru.job4j_url_shortcut.model.UrlRegistration;
import ru.job4j_url_shortcut.service.SimpleUrlRegistrationService;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/url")
public class UrlRegistrationController {

    private final SimpleUrlRegistrationService urlRegistrationService;

    @GetMapping("/redirect/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        UrlRegistration longUrl = this.urlRegistrationService.redirectUrl(code);
        if (longUrl == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.urlRegistrationService.updateTotal(longUrl.getCode());
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl.getUrl()));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<String> convert(@RequestBody UrlRegistration url) {
        UrlRegistration convertUrl = this.urlRegistrationService.convertUrl(url.getUrl());
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(convertUrl.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(convertUrl.getCode());
    }

    @GetMapping("/statistic")
    @ResponseStatus(HttpStatus.OK)
    public List<UrlStatisticsDTO> statistic() {
        return this.urlRegistrationService.findAll();
    }
}
