package ru.practicum.statgateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.ViewStats;
import ru.practicum.statgateway.client.EndpointHitClient;
import ru.practicum.statgateway.factory.ModelFactory;
import ru.practicum.statgateway.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EndpointHitController.class)
public class EndpointHitControllerTest {
    @MockBean
    private EndpointHitClient endpointHitClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private final String start = Util.getStart();
    private final String end =  Util.getEnd();
    private final ViewStats response = ModelFactory.createEndpointHitDtoRes();
    private final Map<String, String> params = Map.of("start", start, "end", end, "unique", "false");
    private final List<String> uris = new ArrayList<>();

    @Test
    void getStatShouldSuccessful() throws Exception {
        when(endpointHitClient.get(any(MockHttpServletRequest.class), eq(params), eq(uris))).thenReturn(List.of(response));

        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .content(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

//        verify(endpointHitClient, times(1)).get(any(MockHttpServletRequest.class), eq(params), eq(uris));
    }

    @Test
    void getStatShouldReturnStatus400WithoutDate() throws Exception {
        when(endpointHitClient.get(any(HttpServletRequest.class), eq(params), eq(uris))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(get("/stats"))
                .andExpect(status().isBadRequest());

        verify(endpointHitClient, times(0)).get(any(HttpServletRequest.class), eq(params), eq(uris));
    }

    @Test
    void getStatShouldReturnStatus400ForStart() throws Exception {
        when(endpointHitClient.get(any(HttpServletRequest.class), eq(params), eq(uris))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(get("/stats")
                        .param("start", "01-01-2021 11:11:11")
                        .param("end", end))
                .andExpect(status().isBadRequest());

        verify(endpointHitClient, times(0)).get(any(HttpServletRequest.class), eq(params), eq(uris));
    }

    @Test
    void getStatShouldReturnStatus400ForEnd() throws Exception {
        when(endpointHitClient.get(any(HttpServletRequest.class), eq(params), eq(uris))).thenThrow(ConstraintViolationException.class);

        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", "01-01-2021 11:11:11"))
                .andExpect(status().isBadRequest());

        verify(endpointHitClient, times(0)).get(any(HttpServletRequest.class), eq(params), eq(uris));
    }

    @Test
    void postHitShouldSuccessful() throws Exception {
        EndpointHitDtoReq body = ModelFactory.createEndpointHitDtoReq();
        when(endpointHitClient.create(eq(body), any(HttpServletRequest.class))).thenReturn(response);
        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(endpointHitClient, times(1)).create(eq(body), any(HttpServletRequest.class));
    }
 }
