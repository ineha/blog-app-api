package com.myproject.blog_app.Controller;

import com.myproject.blog_app.Exception.ResourceNotFoundException;
import com.myproject.blog_app.PayLoad.ApiResponse;
import com.myproject.blog_app.PayLoad.PostDto;
import com.myproject.blog_app.PayLoad.PostResponse;
import com.myproject.blog_app.Service.PostService;
import com.myproject.blog_app.entities.Post;
import com.myproject.blog_app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
/*
This is a post controller. Each post contains @categoryId and @userId, based on these two ids a post will be created in DB linked.
A particular post can be updated, deleted, and get by user and category.
In this controller pagination and sorting is also implemented.
 */
@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/{categoryId}/{userId}")
    public ResponseEntity<Post> createPost(@Valid @RequestBody PostDto postDto,
                                           @PathVariable("categoryId") Integer categoryId,
                                           @PathVariable("userId") Integer userId){
        Post post = postService.createPost(postDto, categoryId, userId);
        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    /*
 this API is to get all posts. where we can apply query parameters given below
 pageNumber: It represents a particular page in which elements could be represented(default value is 0)
 pageSize: It represents how many elements a page should contains(default value is 5)
 sortBy: It represents the sorting of elemnts bases on parameter
     */

    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = "0",required = false)Integer pageNumber,
                                                 @RequestParam(value = "pageSize",defaultValue = "5",required = false)Integer pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue = "postId",required = false)String sortBy ){
       PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize,sortBy);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    //Get posts bases on a particular postId
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById( @PathVariable("postId") Integer postId){
        Post postById = this.postService.getPostById(postId);
        return new ResponseEntity<Post>(postById, HttpStatus.OK);
    }

    //Get all posts based on a particular categoryId
    @GetMapping("/categoryId/{categoryId}")
    public ResponseEntity<List<Post>> getAllPostByCategory( @PathVariable("categoryId") Integer categoryId){
        List<Post> allPostByCategory = this.postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<List<Post>>(allPostByCategory, HttpStatus.OK);
    }
    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<Post>> getAllPostByUser( @PathVariable("userId") Integer userId) {
        List<Post> allPostByUser = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<List<Post>>(allPostByUser, HttpStatus.OK);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable("postId")Integer postId){
        Post post = this.postService.updatePost(postDto,postId);
        return new ResponseEntity<Post>(post,HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> updatePost(@PathVariable("postId")Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("post has been deleted successfully",true),HttpStatus.OK);
    }

}
