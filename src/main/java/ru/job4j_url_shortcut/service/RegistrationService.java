package ru.job4j_url_shortcut.service;

import ru.job4j_url_shortcut.model.AuthRequest;
import ru.job4j_url_shortcut.model.Registration;

public interface RegistrationService {

    Registration findBySite(String site);

    AuthRequest registerSite(String site);

}
