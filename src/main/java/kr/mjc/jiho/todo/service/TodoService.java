package kr.mjc.jiho.todo.service;


import kr.mjc.jiho.todo.Entity.Todo;
import kr.mjc.jiho.todo.Entity.User;
import kr.mjc.jiho.todo.dto.TodoCreateRequest;
import kr.mjc.jiho.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getTodosByUser(User user) {
        return todoRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Todo createTodo(User user, TodoCreateRequest req) {

        Todo todo = Todo.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .dueDate(req.getDueDate())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .user(user)
                .completed(false)
                .build();

        return todoRepository.save(todo);
    }


    @Transactional
    public void toggleCompleted(Long todoId, boolean completed) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("Todo 없음"));

        todo.setCompleted(completed);
    }

    public List<Todo> getTodosByUserAndDate(User user, LocalDate date) {
        return todoRepository.findByUserAndDueDateOrderByCreatedAtDesc(user, date);
    }

    @Transactional
    public String deleteTodo(Long todoId) {
        todoRepository.deleteById(todoId);

        return "redirect:/todos";
    }


}