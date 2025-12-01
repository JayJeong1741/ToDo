package kr.mjc.jiho.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class CalendarDay {

    private int day;
    private LocalDate date;
    private boolean today;

    // ✅ 날짜별 할 일 "제목 리스트"
    private List<CalendarTodoItem> todos;
}