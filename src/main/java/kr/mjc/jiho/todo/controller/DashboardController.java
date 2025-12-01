package kr.mjc.jiho.todo.controller;

import kr.mjc.jiho.todo.Entity.Todo;
import kr.mjc.jiho.todo.Entity.User;
import kr.mjc.jiho.todo.dto.CalendarDay;
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

        List<CalendarDay> calendarDays = new ArrayList<>();

        for (int i = 1; i <= yearMonth.lengthOfMonth(); i++) {
            LocalDate date = yearMonth.atDay(i);

            // ✅ 날짜별 Todo 조회
            List<Todo> todos =
                    todoRepository.findByUserAndDueDateOrderByStartTimeAsc(user, date);

            // ✅ 제목만 추출
            List<String> titles = todos.stream()
                    .map(Todo::getTitle)
                    .toList();

            calendarDays.add(new CalendarDay(
                    i,
                    date,
                    date.equals(now),
                    titles
            ));
        }

        model.addAttribute("loginUser", user);
        model.addAttribute("calendarDays", calendarDays);

        return "dashboard";
    }
}
