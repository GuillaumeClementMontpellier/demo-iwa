package fr.polytech.demo.controllers;

import fr.polytech.demo.models.User;
import fr.polytech.demo.models.VerificationToken;
import fr.polytech.demo.repositories.UserRepository;
import fr.polytech.demo.repositories.VerificationTokenRepository;
import fr.polytech.demo.util.OnCreateUserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;

@Controller
public class ViewController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @GetMapping({"/", "/index"})
    public String home() {
        return "index";
    }

    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @GetMapping({"/changeUser"})
    public String changeUser() {
        return "changeUser";
    }

    @GetMapping({"/listUsers"})
    @Secured("ROLE_ADMIN")
    public String listUsers() {
        return "listUsers";
    }

    @GetMapping({"/register"})
    public String register() {
        return "register";
    }

    @PostMapping({"/doRegister"})
    public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult result) {

        if (userRepository.existsUserByUsername(user.getUsername())) {
            return "register.jsp?user=true";
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.saveAndFlush(user);

            eventPublisher.publishEvent(new OnCreateUserEvent("/", user));

            return "login?waiting=true";
        }

    }

    @GetMapping({"/userConfirm"})
    public String confirmUser(@RequestParam("token") String token) {
        VerificationToken verifToken = verificationTokenRepository.getOne(token);
        if (verifToken != null) {
            Date date = Calendar.getInstance().getTime();
            if (date.before(verifToken.getExpiryDate())) {
                verificationTokenRepository.delete(verifToken);
                User user = userRepository.findByUsername(verifToken.getUsername());
                user.setEnabled(true);
                userRepository.saveAndFlush(user);
                return "login.jsp?confirm=true";
            } else {
                verificationTokenRepository.delete(verifToken);
                return "register.jsp?expired=true";
            }
        } else {
            return "register.jsp?confirm=false";
        }
    }

}
