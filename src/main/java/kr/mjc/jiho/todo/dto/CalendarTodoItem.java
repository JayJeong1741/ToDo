package kr.mjc.jiho.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalendarTodoItem {
    private String title;
    private boolean completed;
}
