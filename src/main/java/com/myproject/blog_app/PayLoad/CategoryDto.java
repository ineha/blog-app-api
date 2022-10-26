package com.myproject.blog_app.PayLoad;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

public class CategoryDto {

    private int categoryId;
    @NotEmpty(message = "CategoryName must not be empty!!")
    private String categoryName;
    @NotEmpty(message = "CategoryDesc must not be empty!!")
    private String categoryDesc;

    public CategoryDto() {
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }
}
