package net.pop.taskemerserver.service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import net.pop.taskemerserver.data.model.User;
import net.pop.taskemerserver.data.repos.UserRepo;
import net.pop.taskemerserver.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final SecurityConfig securityConfig;

    @Autowired
    public UserService(UserRepo userRepo, SecurityConfig securityConfig) {
        this.userRepo = userRepo;
        this.securityConfig = securityConfig;
    }

    public User addUser(User user) {
        if (user.getId() != null && userRepo.existsById(user.getId())) {
            throw new EntityExistsException("User found with ID: " + user.getId());
        }
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
        return userRepo.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepo.existsById(id)) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
        userRepo.deleteById(id);
    }

    public void updateUser(User user) {
        if (!userRepo.existsById(user.getId())) {
            throw new EntityNotFoundException("User not found with ID: " + user.getId());
        }
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
        userRepo.save(user);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

}
