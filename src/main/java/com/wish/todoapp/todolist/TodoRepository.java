package com.wish.todoapp.todolist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    Optional<Todo> findById(int id);
    Optional<Todo> findByTitle(String title);
}
