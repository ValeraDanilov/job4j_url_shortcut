package ru.job4j_url_shortcut.model;

import lombok.*;

@Data
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
}
