package se.lexicon.elmira.data.people;

import se.lexicon.elmira.data.todoItems.ConnectionFactory;
import se.lexicon.elmira.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PeopleRepositoryIDBC implements PeopleDAO {

    private static final String FIND_BY_ID = "SELECT * FROM person WHERE person_id = ?";


    @Override
    public Person create(Person newPerson) {
        return null;
    }

    @Override
    public Collection<Person> findAll() {
        return null;
    }

    @Override
    public Optional<Person> findById(int id) {
        Optional<Person> personOptional = Optional.empty();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = createFindByIdStatement(connection, FIND_BY_ID, id);
             ResultSet resultSet = statement.executeQuery()){

            while (resultSet.next()){
               personOptional = Optional.of( createPersonFromResultSet(resultSet));
            }


            }catch (SQLException ex){
            ex.printStackTrace();
        }
        return personOptional;
        }

    private Person createPersonFromResultSet(ResultSet resultSet) throws SQLException {
        return new Person(
                resultSet.getInt("person_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
    }

    private PreparedStatement createFindByIdStatement(Connection connection, String findById, int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
        statement.setInt(1, id);
        return statement;
    }


    @Override
    public Collection<Person> findByName(String name) {
         List<Person> result  = new ArrayList<Person>();
         Connection connection = null;
         PreparedStatement statement = null;
         ResultSet resultSet = null;

         try{


         }catch (SQLException ex){
             ex.printStackTrace();
         }
         return result;
    }

    @Override
    public Person update(Person updated) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}

