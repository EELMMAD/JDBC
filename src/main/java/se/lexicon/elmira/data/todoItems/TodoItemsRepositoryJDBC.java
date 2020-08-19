package se.lexicon.elmira.data.todoItems;

import se.lexicon.elmira.data.people.PeopleDAO;
import se.lexicon.elmira.data.people.PeopleRepositoryIDBC;
import se.lexicon.elmira.model.Person;
import se.lexicon.elmira.model.TodoItem;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

public class TodoItemsRepositoryJDBC implements  TodoItemsDAO {

    private static final String FIND_BY_ID = "SELECT * FROM todo_item WHERE id = ?";
    public static final String INSERT_WITH_ASSIGNEE = "INSERT INTO todo_item (title, description, deadLine, done, assignee_id) VALUES (?, ?, ?, ?, ?)";
    public static final String INSERT_WITHOUT_ASSIGNEE = "INSERT INTO todo_item (title, description, deadLine, done) VALUES (?, ?, ?, ?)";

    public TodoItem createWithNoAssignee(TodoItem newTodoItem){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement( INSERT_WITHOUT_ASSIGNEE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newTodoItem.getTitle());
            statement.setString(2, newTodoItem.getDescription());
            statement.setObject(3, newTodoItem.getDeadLine());
            statement.setBoolean(4, newTodoItem.isDone());

            statement.execute();
            keySet = statement.getGeneratedKeys();
            while (keySet.next()){
                newTodoItem = new TodoItem(
                        keySet.getInt(1),
                        newTodoItem.getTitle(),
                        newTodoItem.getDescription(),
                        newTodoItem.getDeadLine(),
                        newTodoItem.isDone(),
                        newTodoItem.getAssignee()
                );
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try{
                if(keySet != null){
                    keySet.close();
                }
                if (statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }

            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return newTodoItem;
    }

/*
INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...);
 */


    @Override
    public TodoItem create(TodoItem newTodoItem){

        //We doNot want to persist
        if (newTodoItem.getTodoId() != null){
            throw new IllegalArgumentException();
        }

        //If no assignee use a different method
        if(newTodoItem.getAssignee() == null){
            return createWithNoAssignee(newTodoItem);
        }

        if (newTodoItem.getAssignee().getPersonId() == null){
            PeopleDAO peopleDAO = new PeopleRepositoryIDBC();
            Person persisted = peopleDAO.create(newTodoItem.getAssignee());    //persisted means it han an id
            newTodoItem.setAssignee(persisted);
        }

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet keySet = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(INSERT_WITH_ASSIGNEE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, newTodoItem.getTitle());
            statement.setString(2, newTodoItem.getDescription());
            statement.setObject(3, newTodoItem.getDeadLine());
            statement.setBoolean(4, newTodoItem.isDone());
            statement.setInt(5, newTodoItem.getAssignee().getPersonId());
            statement.execute();

            keySet = statement.getGeneratedKeys();
            while (keySet.next()){
                newTodoItem = new TodoItem(
                        keySet.getInt(1),
                        newTodoItem.getTitle(),
                        newTodoItem.getDescription(),
                        newTodoItem.getDeadLine(),
                        newTodoItem.isDone(),
                        newTodoItem.getAssignee()
                );
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }finally {
            try{
                if(keySet != null){
                    keySet.close();
                }
                if (statement != null){
                    statement.close();
                }
                if(connection != null){
                    connection.close();
                }

            }catch (SQLException exception){
                exception.printStackTrace();
            }
        }
        return newTodoItem;
    }

    @Override
    public Collection<TodoItem> findAll() {
        return null;
    }

    @Override
    public Optional<TodoItem> findById(int id) {
        Optional<TodoItem> todoItemOptional = Optional.empty();

        try(
                Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = createFindByIdStatement(connection, "SELECT * FROM todo_item WHERE todo_id = ? ", id);
            ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()){
              //  TodoItem todoItem = createTodoItemFromResultSet(resultSet);
                //todoItemOptional = Optional.of(todoItem);
                todoItemOptional = Optional.of(createTodoItemFromResultSet(resultSet));
            }

        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return todoItemOptional;
    }

    private TodoItem createTodoItemFromResultSet(ResultSet resultSet) throws SQLException {

        TodoItem todoItem = new TodoItem(
                resultSet.getInt("todo_it"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getObject("deadline", LocalDate.class),
                resultSet.getBoolean("done"),
                null

        );
        PeopleDAO peopleDAO = new PeopleRepositoryIDBC();
        Optional<Person> optionalPerson = peopleDAO.findById(resultSet.getInt("assignne_id"));
        if(optionalPerson.isPresent()){
            todoItem.setAssignee(optionalPerson.get());
        }
        return todoItem;
    }

    private PreparedStatement createFindByIdStatement(Connection connection, String findById, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(findById);
        statement.setInt(1, id);
        return statement;
    }

    @Override
    public Collection<TodoItem> findByDoneStatus(boolean done) {
        return null;
    }

    @Override
    public Collection<TodoItem> findByAssignee(int PersonId) {
        return null;
    }

    @Override
    public Collection<TodoItem> findByAssignee(Person person) {
        return null;
    }

    @Override
    public Collection<TodoItem> findByUnassignedTodoItems() {
        return null;
    }

    @Override
    public TodoItem update(TodoItem update) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}





