package se.lexicon.elmira.model;

import java.time.LocalDate;
import java.util.Objects;

public class TodoItem {
    private  Integer todoId;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private Person assignee;

    public TodoItem(Integer todoId, String title, String description, LocalDate deadLine, boolean done, Person assignee) {
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.done = done;
        this.assignee = assignee;
    }

    public TodoItem(String title, String description, LocalDate deadLine, boolean done, Person assignee) {
       this(null, title, description, deadLine, done, assignee);
    }

    public TodoItem(String title, String description, LocalDate deadLine, boolean done) {
        this(null, title, description, deadLine, done, null);
    }

    public Integer getTodoId() {
        return todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Person getAssignee() {
        return assignee;
    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoItem todoItem = (TodoItem) o;
        return getTodoId() == todoItem.getTodoId() &&
                isDone() == todoItem.isDone() &&
                Objects.equals(getTitle(), todoItem.getTitle()) &&
                Objects.equals(getDescription(), todoItem.getDescription()) &&
                Objects.equals(getDeadLine(), todoItem.getDeadLine()) &&
                Objects.equals(getAssignee(), todoItem.getAssignee());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTodoId(), getTitle(), getDescription(), getDeadLine(), isDone(), getAssignee());
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "todoId=" + todoId +
                ", title='" + title + '\'' +
                ", description=" + description +
                ", deadLine=" + deadLine +
                ", done=" + done +
                ", assignee=" + assignee +
                '}';
    }
}
