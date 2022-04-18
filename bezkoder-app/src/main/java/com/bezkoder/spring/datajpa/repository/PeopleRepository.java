package com.bezkoder.spring.datajpa.repository;

import org.springframework.data.repository.CrudRepository;
import com.bezkoder.spring.datajpa.model.People;
import java.util.List;
import java.util.Optional;

public interface PeopleRepository extends CrudRepository<People, String> {
	Optional<People> findByUuid(String uuid);
	List<People> findAll();
}