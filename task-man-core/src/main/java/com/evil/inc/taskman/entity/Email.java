package com.evil.inc.taskman.entity;


import java.util.Objects;

public class Email {
    private String recipient;
    private String subject;
    private String content;

    public Email(final String recipient, final String subject, final String content) {
        this.recipient = recipient;
        this.subject = subject;
        this.content = content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Email email = (Email) o;
        return Objects.equals(recipient, email.recipient) && Objects.equals(subject,
                                                                            email.subject) && Objects.equals(
                content, email.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient, subject, content);
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(final String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Email{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
