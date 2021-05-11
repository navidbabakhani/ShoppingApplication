package com.egs.shoppingapplication.controler;

import com.egs.shoppingapplication.entity.User;
import com.egs.shoppingapplication.service.UserService;
import com.egs.shoppingapplication.utils.JsonUtil;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users/add")
    public String addUserPage(Model model, Principal principal) {
//        logger.info("User name: {}", principal.getName());

        model.addAttribute("user", new User());
        return "create-user";
    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-user";
        }

        try {
            userService.create(user);
        } catch (Throwable exception) {
            String message = exception.getMessage();
            Throwable cause = exception.getCause();
            if (message != null && message.contains("duplicate key value") || cause instanceof ConstraintViolationException) {
                bindingResult.addError(new FieldError("User", "username", "Username is duplicate"));
                return "sign-up";
            }
            return "redirect:/error-page?msg=" + exception.getMessage();
        }
        return "redirect:/users";
    }

    @RequestMapping("users/delete/{id}")
    public String delete(@PathVariable long id) {
        try {
            userService.delete(id);
        } catch (Throwable exception) {
            return "redirect:/error-page?msg=" + exception.getMessage();
        }
        return "redirect:/users";
    }

    @RequestMapping("users/block/{id}")
    public String block(@PathVariable long id) {
        try {
            userService.blockUser(id);
        } catch (Throwable exception) {
            return "redirect:/error-page?msg=" + exception.getMessage();
        }
        return "redirect:/users";
    }

    @RequestMapping("users/unblock/{id}")
    public String unblock(@PathVariable long id) {
        try {
            userService.unblockUser(id);
        } catch (Throwable exception) {
            return "redirect:/error-page?msg=" + exception.getMessage();
        }
        return "redirect:/users";
    }

    @RequestMapping("users/search/{id}")
    @ResponseBody
    public String search(@PathVariable long id) {
        try {
            return JsonUtil.getJson(userService.find(id));
        } catch (Throwable exception) {
            return exception.getMessage();
        }
    }

    @RequestMapping("users/searchbyemail/{email}")
    @ResponseBody
    public String fetchDataByUsername(@PathVariable String email) {
        try {
            return JsonUtil.getJson(userService.findByEmail(email));
        } catch (Throwable exception) {
            return exception.getMessage();
        }
    }
}