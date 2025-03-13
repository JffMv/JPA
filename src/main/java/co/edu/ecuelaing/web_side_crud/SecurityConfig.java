package co.edu.ecuelaing.web_side_crud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 🚫 Desactiva CSRF para APIs REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 🌍 Habilita CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/register", "/api/auth/login").permitAll() // 🔓 Permitir acceso libre a login y registro
                        .anyRequest().authenticated() // 🔐 Todas las demás rutas requieren autenticación
                )
                .formLogin(login -> login
                        .loginProcessingUrl("/api/auth/login") // 📌 URL donde se envían credenciales
                        .defaultSuccessUrl("/api/auth/session", true) // 🔀 Redirigir tras login exitoso
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout") // 📌 Endpoint para cerrar sesión
                        .logoutSuccessUrl("/api/auth/login") // 🔀 Redirigir tras logout
                        .invalidateHttpSession(true) // 🔄 Invalida la sesión
                        .deleteCookies("JSESSIONID") // 🍪 Borra cookies de sesión
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // 🚀 Permite solo una sesión por usuario
                        .expiredUrl("/api/auth/login") // 🔀 Redirige si la sesión expira
                );

        return http.build();
    }

    // ✅ Configuración explícita de CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // ⚠️ Permite todas las peticiones (ajustar en producción)
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
