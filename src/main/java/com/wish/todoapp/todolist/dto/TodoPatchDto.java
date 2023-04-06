package com.wish.todoapp.todolist.dto;

import lombok.Getter;

@Getter
public class TodoPatchDto {
    private int todoId;
    private String title;
    private int todo_order;
    private boolean completed;

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }
}
