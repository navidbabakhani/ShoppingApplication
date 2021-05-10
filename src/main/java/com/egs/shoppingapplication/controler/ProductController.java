package com.egs.shoppingapplication.controler;

import com.egs.shoppingapplication.entity.Product;
import com.egs.shoppingapplication.entity.ProductReview;
import com.egs.shoppingapplication.entity.User;
import com.egs.shoppingapplication.service.ProductService;
import com.egs.shoppingapplication.service.UserService;
import com.egs.shoppingapplication.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;

    @GetMapping("/products/add")
    public String addProductPage(Model model, Principal principal) {
//        logger.info("User name: {}", principal.getName());

        model.addAttribute("product", new Product());
        return "/create-product";
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/create-product";
        }

        try {
            productService.create(product);
        } catch (Throwable exception) {
            return "redirect:/error-page?msg=" + exception.getMessage();
        }
        return "redirect:/products";
    }

    @RequestMapping("products/delete/{id}")
    public String delete(@PathVariable long id) {
        try {
            productService.delete(id);
        } catch (Throwable exception) {
            return "redirect:/error-page?msg=" + exception.getMessage();
        }
        return "redirect:/products";
    }

    @RequestMapping("products/search/{id}")
    @ResponseBody
    public String search(@PathVariable long id) {
        try {
            return JsonUtil.getJson(productService.find(id));
        } catch (Throwable exception) {
            return exception.getMessage();
        }
    }

    @RequestMapping("products/searchbycode/{code}")
    @ResponseBody
    public String searchByCode(@PathVariable String code) {
        try {
            return JsonUtil.getJson(productService.findByProductCode(code));
        } catch (Throwable exception) {
            return exception.getMessage();
        }
    }

    @RequestMapping("products/searchbyname/{name}")
    @ResponseBody
    public String searchByName(@PathVariable String name) {
        try {
            return JsonUtil.getJson(productService.findByName(name));
        } catch (Throwable exception) {
            return exception.getMessage();
        }
    }

    @RequestMapping("products/searchbyprice/min/{min}")
    @ResponseBody
    public String searchByPriceMin(@PathVariable Double min) {
        try {
            return JsonUtil.getJson(productService.findByPrice(min, Double.MAX_VALUE));
        } catch (Throwable exception) {
            return exception.getMessage();
        }
    }

    @RequestMapping("products/searchbyprice/max/{max}")
    @ResponseBody
    public String searchByPriceMax(@PathVariable Double max) {
        try {
            return JsonUtil.getJson(productService.findByPrice(Double.MIN_VALUE, max));
        } catch (Throwable exception) {
            return exception.getMessage();
        }
    }

    @RequestMapping("products/searchbyprice/min/{min}/max/{max}")
    @ResponseBody
    public String searchByPrice(@PathVariable Double min, @PathVariable Double max) {
        try {
            return JsonUtil.getJson(productService.findByPrice(min, max));
        } catch (Throwable exception) {
            return exception.getMessage();
        }
    }

    @GetMapping("products/review/{id}")
    public String addUserReviewPage(Model model, Principal principal, @PathVariable long id) {
        Assert.notNull(principal, "User Information not found! maybe you need to login");
        String username = principal.getName();
        Assert.notNull(username, "Username is null or empty!");
        Assert.hasLength(username, "Username is null or empty!");

        User currentUser = userService.findByEmail(principal.getName());
        Product product = productService.getProduct(id);
        ProductReview productReview = new ProductReview();

        productReview.setUser(currentUser);
        productReview.setProduct(product);

        model.addAttribute("productReview", productReview);
        return "/create-review";
    }


    @PostMapping("products/review")
    public String addUserReview(@Valid @ModelAttribute("productReview") ProductReview productReview) {
        try {
            productService.addProductReview(productReview);
        } catch (Throwable exception) {
            return "redirect:/error-page?msg=" + exception.getMessage();
        }
        return "redirect:/products";
    }
}