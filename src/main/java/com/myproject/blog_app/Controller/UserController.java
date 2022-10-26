package com.myproject.blog_app.Controller;

import com.myproject.blog_app.PayLoad.ApiResponse;
import com.myproject.blog_app.PayLoad.UserDto;
import com.myproject.blog_app.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService; //yha p isk implementation class k h object ayega, wo implementation class spring run time p bnata h : proxy99

    //POST-create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT -update
    @PutMapping("/{id}")
public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") int id){
        UserDto userDto1 = this.userService.updateUser(userDto, id);
        return new ResponseEntity<>(userDto1,HttpStatus.ACCEPTED);
    }
   @DeleteMapping("/{id}")
   public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") int userId){
       System.out.println("deletion started");
     this.userService.deleteUser(userId);
       System.out.println("After deletion started");
        return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
   }
    //GET - USER GET
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int userId){
        UserDto userById = this.userService.getUserById(userId);
        return new ResponseEntity<UserDto>(userById,HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getUsers(){
        System.out.println("get started");
        List<UserDto> allUsers = this.userService.getAllUsers();
        return new ResponseEntity<>(allUsers,HttpStatus.OK);
    }

}
