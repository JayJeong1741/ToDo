package kr.mjc.jiho.todo.controller;

import kr.mjc.jiho.todo.Entity.Todo;
import kr.mjc.jiho.todo.security.CustomUserDetails;
import kr.mjc.jiho.todo.service.TodoService;
import kr.mjc.jiho.todo.Entity.User;
import kr.mjc.jiho.todo.dto.TodoCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    // ✅ 목록
    @GetMapping
    public String list(@AuthenticationPrincipal CustomUserDetails userDetails,
                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                       LocalDate date,
                       Model model) {

        User loginUser = userDetails.getUser();

        List<Todo> todos;

        if (date != null) {
            todos = todoService.getTodosByUserAndDate(loginUser, date);
            model.addAttribute("selectedDate", date);
        } else {
            todos = todoService.getTodosByUser(loginUser);
        }

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("todos", todos);

        return "todo-list";
    }


    // ✅ 등록 폼 (500 에러 방지)
    @GetMapping("/create")
    public String createForm(@AuthenticationPrincipal CustomUserDetails userDetails,
                             Model model) {

        // ✅ 로그인 안 되어 있으면 강제 로그인으로 보냄
        if (userDetails == null) {
            return "redirect:/login";
        }

        model.addAttribute("loginUser", userDetails.getUser());
        model.addAttribute("todoCreateRequest", new TodoCreateRequest());

        return "todo-create";
    }

    // ✅ 등록 처리
    @PostMapping
    public String create(@AuthenticationPrincipal CustomUserDetails userDetails,
                         TodoCreateRequest req) {

        if (userDetails == null) {
            return "redirect:/login";
        }

        todoService.createTodo(userDetails.getUser(), req);
        return "redirect:/todos";
    }

    @PostMapping("/{id}/toggle")
    @ResponseBody
    public void toggle(@PathVariable Long id,
                       @RequestParam boolean completed) {

        todoService.toggleCompleted(id, completed);
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/todos";
    }


}
