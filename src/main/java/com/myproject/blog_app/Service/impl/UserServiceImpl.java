package com.myproject.blog_app.Service.impl;

import com.myproject.blog_app.Exception.ResourceNotFoundException;
import com.myproject.blog_app.PayLoad.UserDto;
import com.myproject.blog_app.Repositories.UserRepo;
import com.myproject.blog_app.Service.UserService;
import com.myproject.blog_app.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user=this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        User updatedUser = this.userRepo.save(user);

        UserDto userDto1=this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
        UserDto userDto = this.userToDto(user);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = this.userRepo.findAll();
        List<UserDto> userDtos = allUsers.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
//User user=this.userRepo.findById(userId).get();
        this.userRepo.delete(user);
    }
    public User dtoToUser(UserDto userDto){
        User user=this.modelMapper.map(userDto,User.class);
//        User user= new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setAbout(userDto.getAbout());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user){
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
//        UserDto userDto=new UserDto();
//        userDto.setId(user.getId());
//        userDto.setAbout(user.getAbout());
//        userDto.setEmail(user.getEmail());
//        userDto.setName(user.getName());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
