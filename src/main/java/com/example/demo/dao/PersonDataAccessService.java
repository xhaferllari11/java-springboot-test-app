package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("someDB-SQL")
public class PersonDataAccessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int insertPerson(UUID id, Person person) {
        return 0;
    }

    @Override
    public List<Person> getAllPersons() {
        String sqlQuerry = "SELECT id, name FROM person";
        List<Person> persons = jdbcTemplate.query(sqlQuerry, ((resultSet, i) -> {
            return new Person(
                    UUID.fromString(resultSet.getString("id")),
                    resultSet.getString("name"));
        }));
        return persons;
    }

    @Override
    public int deletePersonById(UUID id) {
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return 0;
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        final String sqlQuerry = "SELECT id, name FROM person WHERE id = ?";
        Person requestedPerson = jdbcTemplate.queryForObject(
                sqlQuerry,
                new Object[]{id},
                ((resultSet, i) -> {
                    return new Person(
                            UUID.fromString(resultSet.getString("id")),
                            resultSet.getString("name")
                    );
                }));
        return Optional.ofNullable(requestedPerson);
    }
}
