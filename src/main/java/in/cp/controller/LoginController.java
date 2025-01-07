
package in.cp.controller;
import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // Method to display the login page
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        String sessionSalt = generateSalt();
        model.addAttribute("sessionSalt", sessionSalt);
        return "login"; // This refers to a Thymeleaf template named "login.html"
    }

    // Method to handle login submission
    @PostMapping("/login")
    public String handleLogin(@RequestParam("username") String username, 
                              @RequestParam("password") String password, 
                              @RequestParam("sessionSalt") String sessionSalt, 
                              Model model) {
        // Example: Print received values for debugging
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Session Salt: " + sessionSalt);

        // Perform login validation logic here
        boolean loginSuccessful = validateLogin(username, password, sessionSalt);

        if (loginSuccessful) {
            return "redirect:/user/allUsers"; // Redirect to user's home page on success
        } else {
            model.addAttribute("error", "Invalid username or password.");
            return "login"; // Stay on the login page with an error message
        }
    }

    // Method to generate a random salt
    private String generateSalt() {
        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Example method to validate login credentials
    private boolean validateLogin(String username, String password, String sessionSalt) {
        System.out.println("Validating login...");
        return "testUser".equals(username) && "testPassword".equals(password);
    }
}
