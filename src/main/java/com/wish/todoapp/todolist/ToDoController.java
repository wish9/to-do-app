package com.wish.todoapp.todolist;

import com.wish.todoapp.todolist.dto.TodoPatchDto;
import com.wish.todoapp.todolist.dto.TodoPostDto;
import com.wish.todoapp.utils.UriCreator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class ToDoController {

    private final TodoService todoService;

    private final TodoMapper mapper;

    public ToDoController(TodoService todoService, TodoMapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    @GetMapping("/{todo-id}")
    public ResponseEntity getTodo(@PathVariable("todo-id") @Positive int todoId) {
        Todo todo = todoService.findTodo(todoId);
        return new ResponseEntity<>(mapper.todoToTodoResponseDto(todo), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTodoList() {
        List<Todo> todoList = todoService.findTodoList();
        return new ResponseEntity(mapper.todoListToTodoResponseDtoList(todoList), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postTodo(@RequestBody TodoPostDto todoPostDto) {
        Todo todo = todoService.createTodo(mapper.todoPostDtotoTodo(todoPostDto));
        URI location = UriCreator.createUri("/", todo.getTodoId());

        return ResponseEntity.created(location).body(todo);
    }

    @PatchMapping("/{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") @Positive int todoId,
                                    @RequestBody TodoPatchDto todoPatchDto){
        todoPatchDto.setTodoId(todoId);

        Todo todo = todoService.updateTodo(mapper.todoPatchDtotoTodo(todoPatchDto));

        return new ResponseEntity(mapper.todoToTodoResponseDto(todo), HttpStatus.OK);
    }

    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteMember(
            @PathVariable("todo-id") @Positive int todoId){
        todoService.deleteTodo(todoId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity deleteMember(){
        todoService.deleteAllTodo();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
