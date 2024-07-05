package com.usermanagement.web.rest;

import com.commonlib.util.exception.BadRequestException;
import com.usermanagement.service.UserService;
import com.commonlib.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest()
@AutoConfigureMockMvc
public class UserResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private Set<UserDTO> userDTOSet = new HashSet<>();
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        userDTOSet = Set.of(new UserDTO(1L, "Ahmad"), new UserDTO(2L, "Wael"));
        userDTO = UserDTO.builder().id(1L).name("Ahmad").build();
    }

    @Test
    public void shouldFindAllUsers() throws Exception {

        when(userService.getAll()).thenReturn(userDTOSet);

        String response = """
                [{"id" : 1, "content" : "Ahmad"}, {"id" : 2, "content" : "Wael"}]
                """;
        mockMvc.perform(get("/api/users/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    public void findByIdTest() throws Exception {

        Long userId = 1L;
        when(userService.findById(userId)).
                thenReturn(userDTOSet.stream().filter(userDTO -> userDTO.getId().equals(userId)).findFirst().get());

        String response = """
                {"id" : 1, "content" : "Ahmad"}
                """;
        mockMvc.perform(get("/api/users/id/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    public void findByIdTestThenThrowException() throws Exception {

        Long userId = 1L;
        when(userService.findById(userId)).
                thenThrow(BadRequestException.class);

        mockMvc.perform(get("/api/users/id/{id}", userId))
                .andExpect(status().isBadRequest()) ;
    }

    @Test
    public void createTest() throws Exception {

        userService.create(userDTO);

        String userDTORequestAsString = objectMapper.writeValueAsString(userDTO);

        mockMvc.perform(post("/api/users/create").content(userDTORequestAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated());
    }

    @Test
    public void createUpdate() throws Exception {


        when(userService.update(userDTO)).thenReturn(userDTO);

        String updateApiRequest = objectMapper.writeValueAsString(userDTO);
        String updateApiResponse = objectMapper.writeValueAsString(userDTO);

        ResultActions resultActions =mockMvc.perform(put("/api/users/update")
                        .content(updateApiRequest).contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().json(updateApiResponse));

        assertEquals(resultActions.andReturn().getResponse().getContentAsString(), updateApiResponse);
    }
}