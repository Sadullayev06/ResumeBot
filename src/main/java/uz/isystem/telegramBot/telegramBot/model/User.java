package uz.isystem.telegramBot.telegramBot.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long userId;
    private String contact;
    private String name;
    private String username;
    private Integer age;
    private String email;
}
