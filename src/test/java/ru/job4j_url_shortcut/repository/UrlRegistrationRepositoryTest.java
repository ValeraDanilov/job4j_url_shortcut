package ru.job4j_url_shortcut.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.job4j_url_shortcut.dto.UrlStatisticsDTO;
import ru.job4j_url_shortcut.model.UrlRegistration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@SpringJUnitConfig
class UrlRegistrationRepositoryTest {

    @Autowired
    private UrlRegistrationRepository repository;

    @Test
    void whenFindByUrl_thenReturnUrlRegistration() {
        UrlRegistration urlRegistration = new UrlRegistration();
        urlRegistration.setUrl("http://example.com");
        urlRegistration.setCode("exampleKey");
        urlRegistration.setTotal(100);
        this.repository.save(urlRegistration);
        UrlRegistration found = this.repository.findByUrl("http://example.com");
        assertThat(found).isNotNull();
        assertThat(found.getUrl()).isEqualTo("http://example.com");
    }

    @Test
    void whenFindByKey_thenReturnUrlRegistration() {
        UrlRegistration urlRegistration = new UrlRegistration();
        urlRegistration.setUrl("http://example.com");
        urlRegistration.setCode("exampleKey");
        urlRegistration.setTotal(100);
        this.repository.save(urlRegistration);
        UrlRegistration found = this.repository.findByCode("exampleKey");
        assertThat(found).isNotNull();
        assertThat(found.getCode()).isEqualTo("exampleKey");
    }

    @Test
    void whenFindAllUrlAndTotal_thenReturnUrlStatisticsDTOList() {
        UrlRegistration urlRegistration = new UrlRegistration();
        urlRegistration.setUrl("http://example.com");
        urlRegistration.setCode("exampleKey");
        urlRegistration.setTotal(100);
        this.repository.save(urlRegistration);
        List<UrlStatisticsDTO> stats = this.repository.findAllUrlAndTotal();
        assertThat(stats).isNotEmpty();
        assertThat(stats.get(0).getUrl()).isEqualTo("http://example.com");
        assertThat(stats.get(0).getTotal()).isEqualTo(100);
    }
}
