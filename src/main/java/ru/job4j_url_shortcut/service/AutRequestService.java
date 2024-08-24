package ru.job4j_url_shortcut.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j_url_shortcut.model.AuthRequest;
import ru.job4j_url_shortcut.repository.AuthRequestRepository;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AutRequestService implements UserDetailsService {

    private final AuthRequestRepository authRequestRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthRequest authRequest = this.authRequestRepository.findByLogin(username);
        if (authRequest == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(authRequest.getLogin(), authRequest.getPassword(), new ArrayList<>());
    }
}
