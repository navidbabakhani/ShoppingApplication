package com.egs.shoppingapplication.service;

import com.egs.shoppingapplication.dto.UserRepresentation;
import com.egs.shoppingapplication.entity.User;
import com.egs.shoppingapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void blockUser(long id) {
        setActive(id, false);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void unblockUser(long id) {
        setActive(id, true);
    }

    private void setActive(long id, boolean active) {
        User userInDb = find(id);
        userInDb.setActive(active);
        userRepository.save(userInDb);
    }

    @Override
    public User create(UserRepresentation userRepresentation) {
        final String encryptedPassword = checkPasswordLengthThenEncrypt(userRepresentation.getPassword());
        User user = new User();
        String username = userRepresentation.getUsername();
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Email as a username is mandatory");
        }
        user.setEmail(userRepresentation.getUsername());
        user.setPassword(encryptedPassword);
        user.setActive(true);

        return userRepository.save(user);
    }

    private String checkPasswordLengthThenEncrypt(String password) {
        if (password.length() < 3) {
            //TODO: this exception should be declared as a specific class not RuntimeException
            throw new RuntimeException("Password length should be at least 3 characters");
        }
        return bCryptPasswordEncoder.encode(password);
    }

    @Override
    public User create(User user) {
        final String encryptedPassword = checkPasswordLengthThenEncrypt(user.getPassword());
        String username = user.getEmail();
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Email as a username is mandatory");
        }
        user.setPassword(encryptedPassword);

        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (user.getId() != 0) {
            User userFromDb = find(user.getId()); //make sure user does exist
            return userRepository.save(user);
        } else if (user.getEmail() != null && user.getEmail().isEmpty()) {
            User userFromDb = findByEmail(user.getEmail());
            user.setId(userFromDb.getId());
            return userRepository.save(user);
        }
        throw new ResourceNotFoundException(("The provided user was not found in the database"));
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User find(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("User with id {0} is not found.", id)));
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(() -> new ResourceNotFoundException(MessageFormat.format("User with email {0} is not found.", email)));
    }
}