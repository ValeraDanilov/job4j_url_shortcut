package ru.job4j_url_shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j_url_shortcut.model.AuthRequest;
import ru.job4j_url_shortcut.model.Registration;
import ru.job4j_url_shortcut.repository.AuthRequestRepository;
import ru.job4j_url_shortcut.repository.RegistrationRepository;

import java.security.SecureRandom;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SimpleRegistrationService implements RegistrationService {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final RegistrationRepository registrationRepository;
    private final AuthRequestRepository authRequestRepository;

    public Registration findBySite(String site) {
        return this.registrationRepository.findBySite(site);
    }

    public AuthRequest registerSite(String site) {
        if (findBySite(site) != null) {
            return new AuthRequest(0, null, null, false);
        }

        String login = UUID.randomUUID().toString();
        String rawPassword = generateRandomPassword();
        String encodedPassword = this.passwordEncoder.encode(rawPassword);

        saveSite(site);
        saveLoginPasswordAndStatus(login, encodedPassword, true);

        return new AuthRequest(0, login, rawPassword, true);
    }

    private void saveLoginPasswordAndStatus(String login, String password, boolean status) {
        this.authRequestRepository.save(new AuthRequest(0, login, password, status));
    }

    private void saveSite(String site) {
        this.registrationRepository.save(new Registration(0, site));
    }

    private String generateRandomPassword() {
        SecureRandom random = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
}
