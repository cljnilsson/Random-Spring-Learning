package com.Lukas.demo.repository;

import com.Lukas.demo.model.ToDoItem;
import com.Lukas.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    @Query("SELECT u from User u where u.oauthId=:id and u.oauthProvider=:oauth")
    public User findOneByOauthIdAndProvider(@Param("oauth") String s, @Param("id") String i);

    @Query("SELECT u from User u where u.oauthId=:id")
    public User findOneByOauth(@Param("id") String s);
}
