package kr.mjc.jiho.todo.controller;

import kr.mjc.jiho.todo.Entity.Todo;
import kr.mjc.jiho.todo.Entity.User;
import kr.mjc.jiho.todo.dto.CalendarDay;
import kr.mjc.jiho.todo.dto.CalendarTodoItem;
import kr.mjc.jiho.todo.repository.TodoRepository;
import kr.mjc.jiho.todo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final TodoRepository todoRepository;

    /** ✅ 로그인 성공 후 메인 달력 화면 */
    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal CustomUserDetails userDetails,
                            Model model) {

        User user = userDetails.getUser();

        LocalDate now = LocalDate.now();
        YearMonth yearMonth = YearMonth.from(now);

        // ✅ 1일의 요일 계산
        LocalDate firstDay = yearMonth.atDay(1);
        int dayOfWeek = firstDay.getDayOfWeek().getValue(); // 월=1 ... 일=7
        int blankDaysBefore = dayOfWeek % 7; // ✅ 일요일=0, 월=1 ... 토=6

        List<CalendarDay> calendarDays = new ArrayList<>();

        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            LocalDate date = yearMonth.atDay(i);

            List<Todo> todos =
                    todoRepository.findByUserAndDueDateOrderByStartTimeAsc(user, date);

            List<CalendarTodoItem> todoItems =
                    todos.stream()
                            .map(todo -> new CalendarTodoItem(
                                    todo.getTitle(),
                                    todo.isCompleted()
                            ))
                            .toList();


            calendarDays.add(new CalendarDay(
                    i,
                    date,
                    date.equals(now),
                    todoItems   // ✅ 여기 변경
            ));

        }

        model.addAttribute("loginUser", user);
        model.addAttribute("calendarDays", calendarDays);

        // ✅ 이 한 줄이 핵심
        model.addAttribute("blankDaysBefore", blankDaysBefore);

        return "dashboard";
    }

}
