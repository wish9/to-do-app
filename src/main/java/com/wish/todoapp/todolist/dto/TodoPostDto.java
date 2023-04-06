package com.wish.todoapp.todolist.dto;

import lombok.Getter;

@Getter
public class TodoPostDto {
    private String title;
    private int todo_order;
    private boolean completed;
}
