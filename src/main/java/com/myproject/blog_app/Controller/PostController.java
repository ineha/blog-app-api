package com.myproject.blog_app.Controller;

import com.myproject.blog_app.Config.AppConstants;
import com.myproject.blog_app.Exception.ResourceNotFoundException;
import com.myproject.blog_app.PayLoad.ApiResponse;
import com.myproject.blog_app.PayLoad.FileResponse;
import com.myproject.blog_app.PayLoad.PostDto;
import com.myproject.blog_app.PayLoad.PostResponse;
import com.myproject.blog_app.Service.FileService;
import com.myproject.blog_app.Service.PostService;
import com.myproject.blog_app.entities.Post;
import com.myproject.blog_app.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
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

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

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
 sortBy: It represents the sorting of elements bases on parameter
     */

    @GetMapping("/")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNumber,
                                                 @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy ){
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

    //search method
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<Post>> searchPostByTitle(@PathVariable("keywords") String keywords){
        List<Post> posts = this.postService.searchPosts(keywords);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
    
    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<FileResponse> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId){
        String FileName=null;
        try {
            FileName=  this.fileService.uploadImage(path,image);
           Post post=this.postService.getPostById(postId);
            PostDto postDto = modelMapper.map(post, PostDto.class);
            postDto.setImageName(FileName);
            this.postService.updatePost(postDto,postId);
            return new ResponseEntity<FileResponse>(new FileResponse(FileName,"Image is successfully uploaded"), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new FileResponse(FileName, "Error occurred"),HttpStatus.BAD_REQUEST);

        }
    }


    //method to serve File
    @GetMapping(value = "/images/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(@PathVariable("imageName")String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

}
