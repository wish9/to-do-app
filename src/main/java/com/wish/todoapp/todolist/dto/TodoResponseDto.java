package com.wish.todoapp.todolist.dto;

import lombok.Builder;
import lombok.Getter;

@Builder // 빌더 패턴 사용할 수 있게 추가 // 빌더패턴 = set같은거 쉽게 할 수 있게 도와주는거
@Getter
public class TodoResponseDto {
    private int todoId;
    private String title;
    private  int todo_order;
    private boolean completed;
}
