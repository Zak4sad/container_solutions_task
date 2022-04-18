package com.bezkoder.spring.datajpa;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.List;

import com.bezkoder.spring.datajpa.model.People;
import com.bezkoder.spring.datajpa.repository.PeopleRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;




@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootDataJpaApplicationIntegrationTest {

    @Autowired
    private PeopleRepository peopleRepository;

    // @Test
    // void contextLoads() {}

    @Test
    // @Sql("/test.sql")
    public void getsAllPeople(){
        List<People> response = peopleRepository.findAll();
        assertNotEquals(response, Optional.empty());
        assertEquals(3, response.size());
    }

    @Test
    @Sql("/db/data-test.sql")
    public void getsSinglePeople()throws Exception  {
        Optional<People> response = peopleRepository.findByUuid("00c5f740-a4c0-4513-7554-891facba113b");
        assertEquals("00c5f740-a4c0-4513-7554-891facba113b" , response.get().getUuid());
        assertEquals(false , response.get().isSurvived());
        assertEquals(2 , response.get().getPassengerClass());
        assertEquals("Mr. Zakariaa SADEK" , response.get().getName());
        assertEquals("male" , response.get().getSex());
        assertEquals(25 , response.get().getAge());
        assertEquals(1 , response.get().getSiblingsOrSpousesAboard());
        // to be finished later
    }
    
    @Test
    public void getsSinglePeopleNotFound(){
        Optional<People> response = peopleRepository.findByUuid("00c5f740-0000-0000-0000-891facba113b");
        assertEquals(response, Optional.empty());
    }

    @Test
    public void addsNewPeople() {
        People people = new People();
        people.setUuid("00c5f740-0000-0000-0000-891facba113b");
        people.setSurvived(true);
        people.setPassengerClass(2);
        people.setName("Mr. AbdelKader SADEK");
        people.setSex("male");
        people.setAge(60);
        people.setSiblingsOrSpousesAboard(4);
        people.setParentsOrChildrenAboard(4);
        people.setFare(8.88);
        People response = peopleRepository.save(people);
        assertNotNull(response.getUuid());
        assertEquals("Mr. AbdelKader SADEK" , response.getName());
        assertEquals(60 , response.getAge());
    }

    @Test
    public void updatePeople() {
        ArrayList<String> ids = new ArrayList<String>();
        int listBeforeDelete = peopleRepository.findAll().size();
        for(int i=0;i<listBeforeDelete;i++) {
            ids.add(peopleRepository.findAll().get(i).getUuid());
        }
        Random random = new Random();
        String index = ids.get( random.nextInt(ids.size()));
        Optional<People> person = peopleRepository.findById(index);
        String fullNameBeforeUpdate = person.get().getName();
        People _people = person.get();
		_people.setAge(person.get().getAge());
		_people.setName("Name Update Test");
        peopleRepository.save(_people);
        String fullNameAfterUpdate = person.get().getName();
        assertNotEquals(fullNameBeforeUpdate, fullNameAfterUpdate);
    }

    // @Test
    // public void deleteSinglePeople() {
    //     System.out.println(" -Delete response Before - "+ peopleRepository.findAll());
    // 	ArrayList<String> ids = new ArrayList<String>();
    //     int listBeforeDelete = peopleRepository.findAll().size();
    //     // Add two people if the list is empty
    //     if(listBeforeDelete <= 0) {
    //         People people0 = new People();
    //         People people1 = new People();
    //         // people.setUuid("f0cb7520-4c73-43e0-84b1-19bdc08c0ecb");
    //         people0.setSurvived(true);
    //         people0.setPassengerClass(2);
    //         people0.setName("Mr. AbdelKader SADEK");
    //         people0.setSex("male");
    //         people0.setAge(60);
    //         people0.setSiblingsOrSpousesAboard(4);
    //         people0.setParentsOrChildrenAboard(4);
    //         people0.setFare(8.88);
    //         peopleRepository.save(people0);
    //         listBeforeDelete++;
    //         people1.setSurvived(false);
    //         people1.setPassengerClass(3);
    //         people1.setName("Mlle. Aycha SADEK");
    //         people1.setSex("female");
    //         people1.setAge(54);
    //         people1.setSiblingsOrSpousesAboard(4);
    //         people1.setParentsOrChildrenAboard(4);
    //         people1.setFare(8.88);
    //         peopleRepository.save(people1);
    //         listBeforeDelete++;
    //     }
    //     System.out.println(" -Delete response After - "+ peopleRepository.findAll());
    //     System.out.println(" -listBeforeDelete -> "+ listBeforeDelete+" peopleRepository.findAll().size() "+peopleRepository.findAll().size());

    //     // System.out.println("---->"+peopleRepository.findAll().get(0).getUuid());
    //     // //peopleRepository.deleteByUuid(index);
    //     peopleRepository.deleteByUuid(peopleRepository.findAll().get(0).getUuid().toString());
    //     // //assertEquals(listBeforeDelete-1, peopleRepository.findAll().size());
    // }

    @Test
    public void deleteAllPeople() {
        peopleRepository.deleteAll();
        ArrayList<People> list=new ArrayList<People>();
        list.clear();
        assertEquals(peopleRepository.findAll(), list);
    }
}