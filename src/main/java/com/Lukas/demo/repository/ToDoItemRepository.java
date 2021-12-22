package com.Lukas.demo.repository;

import com.Lukas.demo.model.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long>
{

}
