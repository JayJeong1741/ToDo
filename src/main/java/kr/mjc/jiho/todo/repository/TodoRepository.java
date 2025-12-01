package kr.mjc.jiho.todo.repository;

import kr.mjc.jiho.todo.Entity.Todo;
import kr.mjc.jiho.todo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    // 로그인한 사용자 기준 할 일 목록
    List<Todo> findByUserOrderByCreatedAtDesc(User user);

    // 또는 userId만으로 조회
    List<Todo> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Todo> findByUserAndDueDateOrderByCreatedAtDesc(User user, LocalDate dueDate);

    long countByUserAndDueDate(User user, LocalDate dueDate);
    List<Todo> findByUserAndDueDateOrderByStartTimeAsc(User user, LocalDate dueDate);

}