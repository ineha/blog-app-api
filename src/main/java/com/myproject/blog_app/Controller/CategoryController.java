package com.myproject.blog_app.Controller;

import com.myproject.blog_app.PayLoad.ApiResponse;
import com.myproject.blog_app.PayLoad.CategoryDto;
import com.myproject.blog_app.PayLoad.UserDto;
import com.myproject.blog_app.Service.CategoryService;
import com.myproject.blog_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService; //yha p isk implementation class k h object ayega, wo implementation class spring run time p bnata h : proxy99

    //POST-create user
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = this.categoryService.createUser(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    //PUT -update
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("id") int id){
        CategoryDto categoryDto1= this.categoryService.updateUser(categoryDto, id);
        return new ResponseEntity<>(categoryDto1,HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") int id){
        System.out.println("deletion started");
        this.categoryService.deleteUser(id);
        System.out.println("After deletion started");
        return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted successfully",true),HttpStatus.OK);
    }
    //GET - USER GET
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getUserById(@PathVariable("id") int Id){
        CategoryDto categoryById = this.categoryService.getCategoryById(Id);
        return new ResponseEntity<CategoryDto>(categoryById,HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getUsers(){
        System.out.println("get started");
        List<CategoryDto> allUsers = this.categoryService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }
}
