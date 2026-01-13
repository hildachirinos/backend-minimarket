package com.epiis.app.dataaccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.epiis.app.entity.Person;

public interface PersonRepository extends JpaRepository<Person, String> {
	@Query("""
        select person
        from Person person
        left join fetch person.listPhone phone
    """)
	List<Person> getAll();
}
