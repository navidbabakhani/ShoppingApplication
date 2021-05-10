package com.egs.shoppingapplication.service;

import com.egs.shoppingapplication.entity.Category;
import com.egs.shoppingapplication.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Iterable<Category> getAllCategories() {
        //TODO: can have pagination, since JpaRepository supports pagination and sort
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategory(long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public Category getProductByName(String name) {
        return categoryRepository
                .findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    @Override
    public String delete(long id) {
        Category product = getCategory(id);//to make sure the category exists
        categoryRepository.delete(product);
        return "Category is deleted";
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        Category categoryInDb = getCategory(category.getId());//to make sure the product exists
//        category.setId(categoryInDb.getId());
        return categoryRepository.save(category);
    }
}
