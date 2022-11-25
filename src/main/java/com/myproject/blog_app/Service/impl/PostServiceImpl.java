package com.myproject.blog_app.Service.impl;

import com.myproject.blog_app.Exception.ResourceNotFoundException;
import com.myproject.blog_app.PayLoad.PostDto;
import com.myproject.blog_app.PayLoad.PostResponse;
import com.myproject.blog_app.Repositories.CategoryRepo;
import com.myproject.blog_app.Repositories.PostRepo;
import com.myproject.blog_app.Repositories.UserRepo;
import com.myproject.blog_app.Service.PostService;
import com.myproject.blog_app.entities.Category;
import com.myproject.blog_app.entities.Post;
import com.myproject.blog_app.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
    public Post updatePost(PostDto postDto,Integer postId) {
        Post post = modelMapper.map(postDto, Post.class);
//        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
//        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
//        post.setUser(user);
//        post.setCategory(category);
       // post.setAddedDate(postDto.getAddedDate());
        Post post1 = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post1.setImageName(postDto.getImageName());
        post1.setTitle(post.getTitle());
        post1.setContent(postDto.getContent());
        this.postRepo.save(post1);
        return post1;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
         this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> pagePost = this.postRepo.findAll(p);
        List<Post> allPosts = pagePost.getContent().stream().collect(Collectors.toList());
        PostResponse postResponse = new PostResponse(allPosts,pageNumber,pageSize,allPosts.size() ,pagePost.getTotalPages(), pagePost.isLast() );
        return postResponse;
    }

    @Override
    public Post getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        return post;
    }

    @Override
    public List<Post> getAllPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> byCategory = this.postRepo.findByCategory(category);
        return byCategory;
    }

    @Override
    public List<Post> getAllPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Post> byUser = this.postRepo.findByUser(user);
        return byUser;
    }

    @Override
    public List<Post> searchPosts(String keyword) {
        List<Post> byTitleContaining = this.postRepo.findByTitleContaining(keyword);
        return byTitleContaining;
    }
}
