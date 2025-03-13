package co.edu.ecuelaing.web_side_crud.service;

import co.edu.ecuelaing.web_side_crud.model.User;
import co.edu.ecuelaing.web_side_crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hashing de contraseña
        return userRepository.save(user);
    }

    public boolean authenticate(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }
}
