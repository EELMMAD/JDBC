package se.lexicon.elmira.data.people;

import se.lexicon.elmira.model.Person;

import java.util.Collection;

import java.util.Optional;

public interface PeopleDAO {

    Person create(Person newPerson);
    Collection<Person> findAll();
    Optional<Person> findById(int id);
    Collection<Person> findByName(String name);
    Person update(Person updated);
    boolean deleteById(int id);

}
