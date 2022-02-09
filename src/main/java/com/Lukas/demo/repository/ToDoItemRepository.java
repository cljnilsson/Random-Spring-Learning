package com.Lukas.demo.repository;

import com.Lukas.demo.model.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long>
{

}
