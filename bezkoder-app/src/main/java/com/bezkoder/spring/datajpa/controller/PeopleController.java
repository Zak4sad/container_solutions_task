package com.bezkoder.spring.datajpa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.datajpa.model.People;
import com.bezkoder.spring.datajpa.repository.PeopleRepository;

@RestController
public class PeopleController {

	@Autowired
	PeopleRepository peopleRepository;

	@GetMapping("/people")
	public ResponseEntity<List<People>> getPeople() {
		try {
			List<People> people = new ArrayList<People>();
			
			peopleRepository.findAll().forEach(people::add);

			if (people.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(people, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/people/{uuid}")
	public ResponseEntity<People> getPeopleByUuid(@PathVariable("uuid") String uuid) {
		Optional<People> peopleData = peopleRepository.findByUuid(uuid);

		if (peopleData.isPresent()) {
			return new ResponseEntity<>(peopleData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/people")
	public ResponseEntity<People> createPeople(@RequestBody People people) {
		try {
			People _people = peopleRepository
					.save(new People(
						// people.getUuid(), 
						people.isSurvived(), 
						people.getPassengerClass(), 
						people.getName(), 
						people.getSex(), 
						people.getAge(), 
						people.getSiblingsOrSpousesAboard(), 
						people.getParentsOrChildrenAboard(), 
						people.getFare()
					));
			return new ResponseEntity<>(_people, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/people/{uuid}")
	public ResponseEntity<People> updatePeople(@PathVariable("uuid") String uuid, @RequestBody People people) {
		Optional<People> peopleData = peopleRepository.findByUuid(uuid);

		if (peopleData.isPresent()) {
			People _people = peopleData.get();

			_people.setSurvived(people.isSurvived());
			_people.setPassengerClass(people.getPassengerClass());
			_people.setName(people.getName());
			_people.setSex(people.getSex());
			_people.setAge(people.getAge());
			_people.setSiblingsOrSpousesAboard(people.getSiblingsOrSpousesAboard());
			_people.setParentsOrChildrenAboard(people.getParentsOrChildrenAboard());
			_people.setFare(people.getFare());


			return new ResponseEntity<>(peopleRepository.save(_people), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/people/{uuid}")
	public ResponseEntity<HttpStatus> deletePeople(@PathVariable("uuid") String uuid) {
		try {
			peopleRepository.deleteById(uuid);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/people")
	public ResponseEntity<HttpStatus> deleteAllPeople() {
		try {
			peopleRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
