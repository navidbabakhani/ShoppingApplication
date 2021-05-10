package com.egs.shoppingapplication;

import com.egs.shoppingapplication.entity.User;
import com.egs.shoppingapplication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private WebApplicationContext context;
//
//    private MockMvc mvc;

    // test methods go below
    @Test
    public void shouldCreateUser() {
        User user = new User();
        user.setFirstName("Navid");
        user.setLastName("Babakhani");
        user.setPassword("navid2021");
        user.setEmail("navid@gmail.com");
        user.setActive(true);

        User savedUser = userRepository.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());

        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }

    @Test
    public void shouldBlockUser() {
        User user = new User();
        user.setFirstName("Navid");
        user.setLastName("Babakhani");
        user.setPassword("navid2021");
        user.setEmail("navid" + new Random().nextInt(1000));
        user.setActive(true);

        User savedUser = userRepository.save(user);

        savedUser.setActive(false);
        userRepository.save(user);

        User existUser = entityManager.find(User.class, savedUser.getId());
        assertThat(!existUser.isActive());
    }
}