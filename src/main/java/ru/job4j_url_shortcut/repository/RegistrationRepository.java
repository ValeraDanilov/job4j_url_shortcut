package ru.job4j_url_shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j_url_shortcut.model.Registration;

public interface RegistrationRepository extends CrudRepository<Registration, Integer> {

    Registration findBySite(String site);
}
