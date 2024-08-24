package ru.job4j_url_shortcut.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j_url_shortcut.model.AuthRequest;

public interface AuthRequestRepository extends CrudRepository<AuthRequest, Integer> {

    AuthRequest findByLogin(String username);
}
