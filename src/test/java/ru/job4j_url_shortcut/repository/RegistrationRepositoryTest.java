package ru.job4j_url_shortcut.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.job4j_url_shortcut.model.Registration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RegistrationRepositoryTest {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Test
    void whenFindBySite_thenReturnRegistration() {
        Registration registration = new Registration();
        registration.setSite("example.com");
        this.registrationRepository.save(registration);
        Registration found = this.registrationRepository.findBySite("example.com");
        assertThat(found).isNotNull();
        assertThat(found.getSite()).isEqualTo("example.com");
    }

    @Test
    void whenFindBySite_notFound() {
        Registration found = this.registrationRepository.findBySite("nonexistent.com");
        assertThat(found).isNull();
    }
}
