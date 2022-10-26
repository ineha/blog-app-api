package com.myproject.blog_app.Repositories;

import com.myproject.blog_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
}
