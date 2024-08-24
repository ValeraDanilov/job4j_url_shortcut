package ru.job4j_url_shortcut.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j_url_shortcut.model.AuthRequest;
import ru.job4j_url_shortcut.model.Registration;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j_url_shortcut.service.SimpleRegistrationService;

@RestController
@AllArgsConstructor
@RequestMapping("/reg")
public class RegistrationController {

    private final SimpleRegistrationService registrationService;

    @PostMapping
    public ResponseEntity<AuthRequest> registerUser(@RequestBody Registration registration) {
        AuthRequest authRequest = this.registrationService.registerSite(registration.getSite());
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(registration.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(authRequest);
    }
}
