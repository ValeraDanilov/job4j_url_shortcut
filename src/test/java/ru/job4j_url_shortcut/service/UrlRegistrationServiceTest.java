package ru.job4j_url_shortcut.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.job4j_url_shortcut.dto.UrlStatisticsDTO;
import ru.job4j_url_shortcut.model.UrlRegistration;
import ru.job4j_url_shortcut.repository.UrlRegistrationRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class UrlRegistrationServiceTest {

    @Mock
    private UrlRegistrationRepository urlRegistrationRepository;

    @InjectMocks
    private SimpleUrlRegistrationService urlRegistrationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertUrl() {
        String url = "http://example.com";
        UrlRegistration urlRegistration = new UrlRegistration();
        urlRegistration.setUrl(url);
        urlRegistration.setTotal(0);
        when(this.urlRegistrationRepository.save(any(UrlRegistration.class))).thenReturn(urlRegistration);
        UrlRegistration result = this.urlRegistrationService.convertUrl(url);
        assertNotNull(result.getCode());
        assertEquals(url, result.getUrl());
    }

    @Test
    void testRedirectUrl() {
        String key = "shortcode";
        UrlRegistration urlRegistration = new UrlRegistration();
        urlRegistration.setCode(key);
        urlRegistration.setUrl("http://example.com");
        urlRegistration.setTotal(0);

        when(this.urlRegistrationRepository.findByCode(key)).thenReturn(urlRegistration);

        UrlRegistration result = this.urlRegistrationService.redirectUrl(key);
        assertNotNull(result);
        assertEquals("http://example.com", result.getUrl());
    }

    @Test
    void testFindAll() {
        List<UrlStatisticsDTO> urlStatisticsDTOs = List.of(
                new UrlStatisticsDTO(1, "http://example1.com", 5),
                new UrlStatisticsDTO(2, "http://example2.com", 10)
        );
        when(this.urlRegistrationRepository.findAllUrlAndTotal()).thenReturn(urlStatisticsDTOs);
        List<UrlStatisticsDTO> result = this.urlRegistrationService.findAll();
        assertEquals(urlStatisticsDTOs, result);
    }

    @Test
    void testUpdateTotal() {
        UrlRegistration urlRegistration = new UrlRegistration();
        urlRegistration.setCode("shortcode");
        urlRegistration.setTotal(5);

        when(this.urlRegistrationRepository.findByCode("shortcode")).thenReturn(urlRegistration);
        when(this.urlRegistrationRepository.save(urlRegistration)).thenReturn(urlRegistration);

        this.urlRegistrationService.updateTotal(urlRegistration);
        verify(this.urlRegistrationRepository, times(1)).save(urlRegistration);
    }
}
