package com.wish.todoapp.todolist;

import com.wish.todoapp.todolist.dto.TodoPatchDto;
import com.wish.todoapp.todolist.dto.TodoPostDto;
import com.wish.todoapp.todolist.dto.TodoResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo todoPostDtotoTodo(TodoPostDto todoPostDto);
    Todo todoPatchDtotoTodo(TodoPatchDto todoPatchDto);
    TodoResponseDto todoToTodoResponseDto(Todo todo);
    List<TodoResponseDto> todoListToTodoResponseDtoList(List<Todo> todoList);
}
