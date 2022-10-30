package com.myproject.blog_app.Service;

import com.myproject.blog_app.PayLoad.PostDto;
import com.myproject.blog_app.PayLoad.PostResponse;
import com.myproject.blog_app.entities.Post;

import java.util.List;

public interface PostService {

    //create
    Post createPost(PostDto postDto,Integer categoryId, Integer userId);
    //update
    Post updatePost(PostDto postDto,Integer postId);
    //delete
    void deletePost(Integer postId);
    //get all posts
    PostResponse getAllPost(Integer pageNumber, Integer pageSize,String sortBy);
    //get Post by Id
    Post getPostById(Integer postId);
    //get all post by category
    List<Post> getAllPostByCategory(Integer categoryId);
    //get all posts by user
    List<Post> getAllPostByUser(Integer categoryId);
}
