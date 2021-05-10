package com.egs.shoppingapplication.service;

import com.egs.shoppingapplication.entity.Category;

public interface CategoryService {

    Iterable<Category> getAllCategories();

    Category getCategory(long id);

    Category getProductByName(String name);

    String delete(long id);

    Category create(Category category);

    Category update(Category category);
}