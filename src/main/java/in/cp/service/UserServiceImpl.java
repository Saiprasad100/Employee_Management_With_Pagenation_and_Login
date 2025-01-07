package in.cp.service;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import in.cp.config.SHA256PasswordEncoder;
import in.cp.entity.User_Data;
import in.cp.repo.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private SHA256PasswordEncoder passwordEncoder; // Add the SHA256PasswordEncoder

    @Override
    public void createUser(User_Data user) {
        // Generate salt for the user
        String salt = passwordEncoder.generateSalt();

        // Hash the password with the generated salt
        String hashedPassword = passwordEncoder.hashPassword(user.getPassword(), salt);

        // Set the hashed password and salt to the user
        user.setPassword(hashedPassword);
        user.setSalt(salt); 

        repo.save(user);
    }

    @Override
    public List<User_Data> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public Optional<User_Data> getOneUser(Integer id) {
        return repo.findById(id);
    }

  private String generateSalt() {
        // Use a secure random generator for salt
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

@Override
public Page<User_Data> getUsersWithPagination(Pageable pageable) {
	
	return repo.findAll(pageable);
}

@Override
public Page<User_Data> getUsers(int page, int size, String keyword) {
    Pageable pageable = PageRequest.of(page, size);
    if (keyword != null && !keyword.isEmpty()) {
        return repo.findByEmailContainingIgnoreCase(keyword, pageable);  // Example search query
    }
    return repo.findAll(pageable);
}
}
