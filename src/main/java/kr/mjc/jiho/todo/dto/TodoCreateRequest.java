package kr.mjc.jiho.todo.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TodoCreateRequest {

    private String title;
    private String description;
    private LocalDate dueDate;

    // ✅ 추가
    private LocalTime startTime;
    private LocalTime endTime;
}
