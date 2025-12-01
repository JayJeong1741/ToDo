package kr.mjc.jiho.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    private String username;
    private String password;
    private String name;
}
