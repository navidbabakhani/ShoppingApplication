package com.egs.shoppingapplication.controler;

import com.egs.shoppingapplication.entity.Category;
import com.egs.shoppingapplication.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import java.text.MessageFormat;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("categories/add")
    public String createCategory(Category category) {
        Category categoryAfterSave = categoryService.create(category);
        // log:  MessageFormat.format("Category id {0} is created successfully", categoryAfterSave.getId())
        return "redirect:/categories";
    }

    @PostMapping("categories/update")
    public String updateCategory(Category category) {
        Category categoryAfterUpdate = categoryService.update(category);
        //log: MessageFormat.format("Category id {0} is updated successfully", categoryAfterUpdate.getId())
        return "redirect:/categories";
    }
}