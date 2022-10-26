package com.myproject.blog_app.Service;

import com.myproject.blog_app.PayLoad.UserDto;
import com.myproject.blog_app.entities.User;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);

}
