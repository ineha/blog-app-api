package com.myproject.blog_app.Service.impl;

import com.myproject.blog_app.Exception.ResourceNotFoundException;
import com.myproject.blog_app.PayLoad.CategoryDto;
import com.myproject.blog_app.Repositories.CategoryRepo;
import com.myproject.blog_app.Service.CategoryService;
import com.myproject.blog_app.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CategoryDto createUser(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category save = this.categoryRepo.save(category);
        CategoryDto categoryDto1 = modelMapper.map(save,CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto updateUser(CategoryDto categoryDto, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        category.setCategoryName(categoryDto.getCategoryName());
        category.setCategoryDesc(categoryDto.getCategoryDesc());
        CategoryDto map = modelMapper.map(category, CategoryDto.class);
        return map;
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        CategoryDto map = modelMapper.map(category, CategoryDto.class);
        return map;
    }

    @Override
    public List<CategoryDto> getAllUsers() {
        List<Category> allUsers = this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = allUsers.stream().map(category -> this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());

        return categoryDtos;
    }

    @Override
    public void deleteUser(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        this.categoryRepo.delete(category);

    }
}
