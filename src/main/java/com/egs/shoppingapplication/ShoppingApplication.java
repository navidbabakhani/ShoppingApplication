package com.egs.shoppingapplication;

import com.egs.shoppingapplication.entity.Product;
import com.egs.shoppingapplication.entity.User;
import com.egs.shoppingapplication.entity.enums.UserRole;
import com.egs.shoppingapplication.repository.ProductRepository;
import com.egs.shoppingapplication.repository.UserRepository;
import com.egs.shoppingapplication.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Collections;

@SpringBootApplication
public class ShoppingApplication {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShoppingApplication.class, args);
    }

    /**
     * Pre-load the system with employees and items.
     */
    public @PostConstruct
    void init() {
        if (productRepository.count() == 0) {
            productRepository.save(new Product("p-code-1", "Uniball Pen", "This is a great pen",
                    13.2d, Collections.singletonList(""), null, LocalDate.now()));
            productRepository.save(new Product("p-code-2", "Fabercastle Pen", "This is another great pen",
                    12.2d, Collections.singletonList(""), null, LocalDate.now()));
            productRepository.save(new Product("p-code-3", "Pencil", "This is a great pencil",
                    123.2d, Collections.singletonList(""), null, LocalDate.now()));
        }

        if (!userRepository.findByEmail("admin").isPresent()) {
            User adminUser = new User("admin", "a", true, UserRole.ADMIN);
            adminUser.setEmail("admin");
            adminUser.setPassword("$2a$10$rGjXdgOk56ULAC55NR2hDep2aDHNDDaCPrmJHgVdTcO8E2VnKnAhS"); // = 123

            userRepository.save(adminUser);
        }
        /**
         * Due to method-level protections on {@link example.company.ItemRepository}, the security context must be loaded
         * with an authentication token containing the necessary privileges.
         */
        SecurityUtils.runAs("admin", "admin", "ROLE_ADMIN");

        SecurityContextHolder.clearContext();
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }

        };
    }
}
