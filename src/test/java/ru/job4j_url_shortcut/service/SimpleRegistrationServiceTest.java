package ru.job4j_url_shortcut.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.job4j_url_shortcut.model.AuthRequest;
import ru.job4j_url_shortcut.model.Registration;
import ru.job4j_url_shortcut.repository.AuthRequestRepository;
import ru.job4j_url_shortcut.repository.RegistrationRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SimpleRegistrationServiceTest {

    @InjectMocks
    private SimpleRegistrationService simpleRegistrationService;

    @Mock
    private RegistrationRepository registrationRepository;

    @Mock
    private AuthRequestRepository authRequestRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(this.passwordEncoder.encode(any(String.class)))
                .thenAnswer(invocation -> "encoded-" + invocation.getArgument(0));
    }

    @Test
    void testFindBySiteWhenSiteExists() {
        String site = "example.com";
        Registration registration = new Registration(0, site);
        when(this.registrationRepository.findBySite(site)).thenReturn(registration);

        Registration result = this.simpleRegistrationService.findBySite(site);

        assertNotNull(result);
        assertEquals(site, result.getSite());
    }

    @Test
    void testFindBySiteWhenSiteDoesNotExist() {
        String site = "nonexistent.com";
        when(this.registrationRepository.findBySite(site)).thenReturn(null);

        Registration result = this.simpleRegistrationService.findBySite(site);

        assertNull(result);
    }

    @Test
    void testRegisterSiteWhenSiteExists() {
        String site = "example.com";
        when(registrationRepository.findBySite(site)).thenReturn(new Registration(0, site));

        AuthRequest result = simpleRegistrationService.registerSite(site);

        assertNotNull(result);
        assertEquals(0, result.getId());
        assertNull(result.getLogin());
        assertNull(result.getPassword());
        assertFalse(result.isStatus());

        verify(registrationRepository, never()).save(any(Registration.class));
        verify(authRequestRepository, never()).save(any(AuthRequest.class));
    }

    @Test
    void testRegisterSiteWhenSiteDoesNotExist() {
        String site = "newsite.com";
        String rawPassword = "randomPassword";
        String encodedPassword = "encoded-" + rawPassword;
        when(registrationRepository.findBySite(site)).thenReturn(null);
        when(passwordEncoder.encode(any(String.class))).thenReturn(encodedPassword);
        AuthRequest result = simpleRegistrationService.registerSite(site);
        assertNotNull(result);
        assertEquals(0, result.getId());
        assertNotNull(result.getLogin());
        assertNotNull(result.getPassword());
        assertTrue(result.isStatus());
        verify(registrationRepository).save(eq(new Registration(0, site)));
        verify(authRequestRepository).save(eq(new AuthRequest(0, result.getLogin(), encodedPassword, true)));
    }
}
