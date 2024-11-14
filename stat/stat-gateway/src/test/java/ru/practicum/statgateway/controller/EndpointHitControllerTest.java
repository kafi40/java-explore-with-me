package ru.practicum.statgateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.statcommon.dto.EndpointHitDtoReq;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.statgateway.client.EndpointHitClient;
import ru.practicum.statgateway.factory.ModelFactory;
import ru.practicum.statgateway.util.Util;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = EndpointHitController.class)
public class EndpointHitControllerTest {
    @MockBean
    private EndpointHitClient endpointHitClient;
    @MockBean
    private HttpServletRequest httpServletRequest;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private final String start = Util.getStart();
    private final String end =  Util.getEnd();
    private final EndpointHitDtoRes response = ModelFactory.createEndpointHitDtoRes();

    @Test
    void getStatShouldSuccessful() throws Exception {
        when(endpointHitClient.get(httpServletRequest)).thenReturn(List.of(response));

        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .content(objectMapper.writeValueAsString(List.of(response)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(endpointHitClient, times(1)).get(httpServletRequest);
    }

    @Test
    void getStatShouldReturnStatus400WithoutDate() throws Exception {
        when(endpointHitClient.get(httpServletRequest)).thenReturn(List.of(response));

        mockMvc.perform(get("/stats"))
                .andExpect(status().isBadRequest());

        verify(endpointHitClient, times(0)).get(httpServletRequest);
    }

    @Test
    void getStatShouldReturnStatus400ForStart() throws Exception {
        when(endpointHitClient.get(httpServletRequest)).thenReturn(List.of(response));

        mockMvc.perform(get("/stats")
                        .param("start", "01-01-2021 11:11:11")
                        .param("end", end))
                .andExpect(status().isBadRequest());

        verify(endpointHitClient, times(0)).get(httpServletRequest);
    }

    @Test
    void getStatShouldReturnStatus400ForEnd() throws Exception {
        when(endpointHitClient.get(httpServletRequest)).thenReturn(List.of(response));

        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", "01-01-2021 11:11:11"))
                .andExpect(status().isBadRequest());

        verify(endpointHitClient, times(0)).get(httpServletRequest);
    }

    @Test
    void postHitShouldSuccessful() throws Exception {
        EndpointHitDtoReq body = ModelFactory.createEndpointHitDtoReq();
        when(endpointHitClient.create(body, httpServletRequest)).thenReturn(response);
        mockMvc.perform(post("/hit")
                        .content(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(endpointHitClient, times(0)).create(body, httpServletRequest);
    }
 }
