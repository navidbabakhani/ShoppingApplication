package com.egs.shoppingapplication;

import com.egs.shoppingapplication.dto.UserRepresentation;
import com.egs.shoppingapplication.entity.Category;
import com.egs.shoppingapplication.entity.Product;
import com.egs.shoppingapplication.entity.User;
import com.egs.shoppingapplication.service.CategoryService;
import com.egs.shoppingapplication.service.ProductService;
import com.egs.shoppingapplication.service.UserService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AppController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }

    //
    @GetMapping("/sign-up")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRepresentation());

        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String registerNewUser(@Valid @ModelAttribute("user") UserRepresentation userRepresentation, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        if (!userRepresentation.getPassword().equals(userRepresentation.getRepeatPassword())) {
            bindingResult.reject("password", "Password mismatch!");
            return "sign-up";
        }

        try {
            userService.create(userRepresentation);
        } catch (Exception exception) {
            String message = exception.getMessage();
            Throwable cause = exception.getCause();
            if (message != null && message.contains("duplicate key value") || cause instanceof ConstraintViolationException) {
                bindingResult.addError(new FieldError("User", "username", "Username is duplicate"));
                return "sign-up";
            }
            return "redirect:/error?msg=" + message;
        }
        return "redirect:/login";
    }

    @GetMapping("/")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/products")
    public String productsPage(Model model, Principal principal) {
//        logger.info("User name: {}", principal.getName());

        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("product", new Product());
        return "products";
    }

    @GetMapping("/categories")
    public String categoriesPage(Model model, Principal principal) {
//        logger.info("User name: {}", principal.getName());

        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("category", new Category());
        return "categories";
    }

    @GetMapping("/users")
    public String usersPage(Model model, Principal principal) {
//        logger.info("User name: {}", principal.getName());

        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "users";
    }
}