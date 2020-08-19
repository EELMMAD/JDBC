package se.lexicon.elmira.data.todoItems;

import se.lexicon.elmira.model.Person;
import se.lexicon.elmira.model.TodoItem;

import java.util.Collection;
import java.util.Optional;

public interface TodoItemsDAO {
    TodoItem create (TodoItem newTodoItem);
    Collection<TodoItem> findAll();
    Optional<TodoItem> findById (int id);
    Collection<TodoItem> findByDoneStatus (boolean done);
    Collection<TodoItem> findByAssignee (int PersonId);
    Collection<TodoItem> findByAssignee (Person person);
    Collection<TodoItem> findByUnassignedTodoItems();
    TodoItem update(TodoItem update);
    boolean deleteById(int id);
}
