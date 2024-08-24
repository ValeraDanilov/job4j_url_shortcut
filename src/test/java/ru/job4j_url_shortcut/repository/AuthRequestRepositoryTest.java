package ru.job4j_url_shortcut.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j_url_shortcut.model.AuthRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class AuthRequestRepositoryTest {

    @Autowired
    private AuthRequestRepository authRequestRepository;

    @Test
    @Transactional
    @Rollback
    void whenSaveUserThenFindByLogin() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setLogin("testuser");
        authRequest.setPassword("testpassword");
        authRequest.setStatus(true);
        this.authRequestRepository.save(authRequest);
        AuthRequest found = this.authRequestRepository.findByLogin("testuser");
        assertNotNull(found);
        assertEquals(found.getLogin(), "testuser");
    }
}
