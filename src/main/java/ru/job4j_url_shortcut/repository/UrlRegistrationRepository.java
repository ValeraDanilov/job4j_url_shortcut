package ru.job4j_url_shortcut.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.job4j_url_shortcut.dto.UrlStatisticsDTO;
import ru.job4j_url_shortcut.model.UrlRegistration;

import java.util.List;

public interface UrlRegistrationRepository extends CrudRepository<UrlRegistration, Integer> {

    UrlRegistration findByUrl(String url);

    @Query(
            """
                    select url from UrlRegistration url
                    where url.code = :key
                    """
    )
    UrlRegistration findByCode(String key);

    @Query("select new ru.job4j_url_shortcut.dto.UrlStatisticsDTO(u.id, u.url, u.total) from UrlRegistration u")
    List<UrlStatisticsDTO> findAllUrlAndTotal();
}
