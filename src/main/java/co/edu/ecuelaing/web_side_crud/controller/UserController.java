package co.edu.ecuelaing.web_side_crud.controller;

import co.edu.ecuelaing.web_side_crud.model.User;
import co.edu.ecuelaing.web_side_crud.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 🔹 REGISTRO DE USUARIO
    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<Map<String, String>> register(@RequestBody User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username and password are required"));
        }

        userService.registerUser(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    // 🔹 LOGIN (CREA SESIÓN Y ENVÍA JSESSIONID)
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpServletRequest request) {
        if (user.getUsername() == null || user.getUsername().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Username and password are required"));
        }

        boolean authenticated = userService.authenticate(user.getUsername(), user.getPassword());

        if (authenticated) {
            request.getSession(true); // 🔥 Crea la sesión en el servidor
            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
    }

    // 🔹 VERIFICAR SESIÓN
    @GetMapping(value = "/session", produces = "application/json")
    public ResponseEntity<Map<String, String>> checkSession(HttpServletRequest request) {
        return request.getSession(false) != null
                ? ResponseEntity.ok(Map.of("message", "Session active"))
                : ResponseEntity.status(401).body(Map.of("message", "No active session"));
    }

    // 🔹 LOGOUT (INVALIDA SESIÓN Y ELIMINA COOKIE)
    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate(); // 🔥 Invalida la sesión
        response.setHeader("Set-Cookie", "JSESSIONID=; Path=/; HttpOnly; Max-Age=0"); // 🔥 Borra la cookie
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}
