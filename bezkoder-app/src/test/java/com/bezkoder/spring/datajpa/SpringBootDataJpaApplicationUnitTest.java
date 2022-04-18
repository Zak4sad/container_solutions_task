package com.bezkoder.spring.datajpa;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith( SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc

public class SpringBootDataJpaApplicationUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getsAllPeople() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/people")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getsSinglePeople() throws Exception {
        String uuid="00c5f740-a4c0-4513-7554-891facba113b";
        mockMvc.perform(MockMvcRequestBuilders.get("/people/"+uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void returnsNotFoundForInvalidSinglePeople() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/people/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void addsNewPeople() throws Exception {
        String newPeople = "{\"survived\":false,\"passengerClass\":3,\"name\":\"Zakariaa SADEK\",\"sex\":\"male.\",\"age\":25,\"siblingsOrSpousesAboard\":2,\"parentsOrChildrenAboard\":2,\"fare\":9.25}";
        mockMvc.perform(MockMvcRequestBuilders.post("/people")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newPeople)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void updatePeople() throws Exception {
        String uuid="00c5f740-a4c0-4513-7554-891facba113b";
        String updatedPeople = "{\"survived\":true,\"passengerClass\":3,\"name\":\"Wafaa SADEK\",\"sex\":\"female\"}";
        mockMvc.perform(MockMvcRequestBuilders.put("/people/"+uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedPeople)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void deleteSinglePeople() throws Exception {
        String uuid="00c5f741-a4c1-4513-891f-7554acba113b";
        mockMvc.perform(MockMvcRequestBuilders.delete("/people/"+uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}