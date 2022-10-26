package com.myproject.blog_app.Service;

import com.myproject.blog_app.PayLoad.CategoryDto;
import com.myproject.blog_app.PayLoad.UserDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createUser(CategoryDto categoryDto);
    CategoryDto updateUser(CategoryDto categoryDto, Integer categoryId);
    CategoryDto getCategoryById(Integer categoryId);
    List<CategoryDto> getAllUsers();
    void deleteUser(Integer categoryId);
}
