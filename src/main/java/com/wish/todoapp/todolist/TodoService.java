package com.wish.todoapp.todolist;

import com.wish.todoapp.exception.BusinessLogicException;
import com.wish.todoapp.exception.ExceptionCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo){
        verifyExistsTitle(todo.getTitle());

        return todoRepository.save(todo);
    }

    public Todo updateTodo(Todo todo){
        Todo findTodo = findVerfiedTodo(todo.getTodoId()); // 바꾸려는 대상이 존재하는지 확인
        verifyExistsTitle(todo.getTitle()); // 바꾸려는 title이 이미 존재하는지 확인

        Optional.ofNullable(todo.getTitle())
                .ifPresent(title -> findTodo.setTitle(title));
        Optional.ofNullable(todo.getTodo_order())
                .ifPresent(order -> findTodo.setTodo_order(order));
        Optional.ofNullable(todo.getCompleted())
                .ifPresent(completed -> findTodo.setCompleted(completed));

        return todoRepository.save(findTodo);
    }

    public Todo findTodo(int todoId){
        return findVerfiedTodo(todoId);
    }

    public List<Todo> findTodoList(){
        return todoRepository.findAll();
    }

    public void deleteTodo(int todoId){
        Todo findTodo = findVerfiedTodo(todoId);
        todoRepository.delete(findTodo);
    }

    public void deleteAllTodo(){
        todoRepository.deleteAll();
    }

    public Todo findVerfiedTodo(int todoId){
        Optional<Todo> optionalTodo =
                todoRepository.findById(todoId);
        Todo findTodo =
                optionalTodo.orElseThrow(() -> new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));

        return findTodo;
    }

    private void verifyExistsTitle(String title){
        Optional<Todo> todo = todoRepository.findByTitle(title);
        if(todo.isPresent()) throw new BusinessLogicException(ExceptionCode.TODO_EXISTS);
    }
}
