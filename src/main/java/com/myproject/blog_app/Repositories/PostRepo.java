package com.myproject.blog_app.Repositories;

import com.myproject.blog_app.entities.Category;
import com.myproject.blog_app.entities.Post;
import com.myproject.blog_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
  List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

}
