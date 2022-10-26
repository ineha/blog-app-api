package com.myproject.blog_app.Service.impl;

import com.myproject.blog_app.Exception.ResourceNotFoundException;
import com.myproject.blog_app.PayLoad.PostDto;
import com.myproject.blog_app.Repositories.CategoryRepo;
import com.myproject.blog_app.Repositories.PostRepo;
import com.myproject.blog_app.Repositories.UserRepo;
import com.myproject.blog_app.Service.PostService;
import com.myproject.blog_app.entities.Category;
import com.myproject.blog_app.entities.Post;
import com.myproject.blog_app.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PostServiceImpl implements PostService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public Post createPost(PostDto postDto,Integer categoryId, Integer userId) {
        Post post = modelMapper.map(postDto, Post.class);
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post save = this.postRepo.save(post);
       return post;
    }

    @Override
    public Post updatePost(PostDto postDto, Integer postId) {
        return null;
    }

    @Override
    public void deletePost(Integer postId) {

    }

    @Override
    public List<Post> getAllPost() {
        return null;
    }

    @Override
    public Post getPostById(Integer postId) {
        return null;
    }

    @Override
    public List<Post> getAllPostByCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<Post> getAllPostByUser(Integer categoryId) {
        return null;
    }
}
