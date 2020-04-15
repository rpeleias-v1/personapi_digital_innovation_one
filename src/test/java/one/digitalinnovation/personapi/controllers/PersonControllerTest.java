package one.digitalinnovation.personapi.controllers;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.services.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static one.digitalinnovation.personapi.utils.PersonUtils.asJsonString;
import static one.digitalinnovation.personapi.utils.PersonUtils.createFakeDTO;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTest {

    private static final String PEOPLE_API_URL_PATH = "/api/v1/people";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonService personService;

    @Test
    void testWhenPOSTIsCalledThenAPersonShouldBeCreated() throws Exception {
        PersonDTO expectedPersonDTO = createFakeDTO();
        MessageResponseDTO expectedResponseMessage = createMessageResponse("Person successfully created with ID", 1L);

        when(personService.create(expectedPersonDTO)).thenReturn(expectedResponseMessage);

        mockMvc.perform(post(PEOPLE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedPersonDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedResponseMessage.getMessage())));
    }

    @Test
    void testWhenGETWithValidIsCalledThenAPersonShouldBeReturned() throws Exception {
        var expectedValidId = 1L;
        PersonDTO expectedPersonDTO = createFakeDTO();
        expectedPersonDTO.setId(expectedValidId);

        when(personService.findById(expectedValidId)).thenReturn(expectedPersonDTO);

        mockMvc.perform(get(PEOPLE_API_URL_PATH + "/" + expectedValidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Rodrigo")))
                .andExpect(jsonPath("$.lastName", is("Peleias")));
    }

    @Test
    void testWhenGETWithInvalidIsCalledThenAnErrorMessagenShouldBeReturned() throws Exception {
        var expectedValidId = 1L;
        PersonDTO expectedPersonDTO = createFakeDTO();
        expectedPersonDTO.setId(expectedValidId);

        when(personService.findById(expectedValidId)).thenThrow(PersonNotFoundException.class);

        mockMvc.perform(get(PEOPLE_API_URL_PATH + "/" + expectedValidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testWhenGETIsCalledThenPeopleListShouldBeReturned() throws Exception {
        var expectedValidId = 1L;
        PersonDTO expectedPersonDTO = createFakeDTO();
        expectedPersonDTO.setId(expectedValidId);
        List<PersonDTO> expectedPeopleDTOList = Collections.singletonList(expectedPersonDTO);

        when(personService.listAll()).thenReturn(expectedPeopleDTOList);

        mockMvc.perform(get(PEOPLE_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("Rodrigo")))
                .andExpect(jsonPath("$[0].lastName", is("Peleias")));
    }

    @Test
    void testWhenPUTIsCalledThenAPersonShouldBeUpdated() throws Exception {
        var expectedValidId = 1L;
        PersonDTO expectedPersonDTO = createFakeDTO();
        MessageResponseDTO expectedResponseMessage = createMessageResponse("Person successfully updated with ID", 1L);

        when(personService.update(expectedValidId, expectedPersonDTO)).thenReturn(expectedResponseMessage);

        mockMvc.perform(put(PEOPLE_API_URL_PATH + "/" + expectedValidId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedPersonDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedResponseMessage.getMessage())));
    }

    @Test
    void testWhenDELETEIsCalledThenAPersonShouldBeDeleted() throws Exception {
        var expectedValidId = 1L;

        mockMvc.perform(delete(PEOPLE_API_URL_PATH + "/" + expectedValidId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private MessageResponseDTO createMessageResponse(String message, Long id) {
        return MessageResponseDTO.builder()
                .message(message + id)
                .build();
    }
}
