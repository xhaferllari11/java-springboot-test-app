package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakePersonDataAccessService implements PersonDao {

    private static List<Person> DB = new ArrayList<>();

    @Override
    public int insertPerson(UUID id, Person person) {
        DB.add(new Person(id,person.getName()));
        return 1;
    }

    @Override
    public List<Person> getAllPersons() {
        return DB;
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> isPerson = getPersonById(id);
        if (!isPerson.isPresent()){
            return 0;
        }
        DB.remove(isPerson.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person newPerson) {
        return getPersonById(id)
                .map(p -> {
                     int index = DB.indexOf(p);
                     if (index >= 0){
                         DB.set(index, new Person(id, newPerson.getName()));
                         return 1;
                     }
                     return 0;
                })
                .orElse(0);
    }

    @Override
    public Optional<Person> getPersonById(UUID id) {
        return DB.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }


}
