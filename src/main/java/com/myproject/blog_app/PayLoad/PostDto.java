package com.myproject.blog_app.PayLoad;

import com.myproject.blog_app.entities.Category;
import com.myproject.blog_app.entities.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

public class PostDto {

    @NotEmpty(message = "title must not be empty")
    private String title;
    @NotEmpty(message = "content must not be empty")
    private String content;
    private String imageName;
    @DateTimeFormat
    private Date addedDate;
    private User user;
    private Category category;

    public PostDto() {
    }

    public PostDto(String title, String content, String imageName, Date addedDate, User user, Category category) {
        this.title = title;
        this.content = content;
        this.imageName = imageName;
        this.addedDate = addedDate;
        this.user = user;
        this.category = category;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
