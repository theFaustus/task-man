package com.evil.inc.taskman.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Email {
    private String recipient;
    private String subject;
    private String content;
}
