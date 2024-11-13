package ru.practicum.stateserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.statcommon.dto.EndpointHitDtoRes;
import ru.practicum.stateserver.factory.ModelFactory;
import ru.practicum.stateserver.service.EndpointHitService;
import ru.practicum.stateserver.util.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EndpointHitController.class)
public class EndpointHitControllerTest {
    @MockBean
    private EndpointHitService endpointHitService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private final String start = Util.getStart();
    private final String end =  Util.getEnd();
    private final EndpointHitDtoRes response = ModelFactory.createEndpointHitDtoRes();;

    @Test
    void testGetStats() throws Exception {
        when(endpointHitService.getStats(start, end)).thenReturn(List.of(response));
        mockMvc.perform(get("/stats")
                    .param("start", start)
                    .param("end", end)
                    .content(objectMapper.writeValueAsString(response))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(response.app()), String.class))
                .andExpect(jsonPath("$[0].uri", is(response.uri()), String.class))
                .andExpect(jsonPath("$[0].hits", is(response.hits()), Integer.class));
        verify(endpointHitService, times(1)).getStats(start, end);
    }

    @Test
    void testGetStatsByUris() throws Exception {
        when(endpointHitService.getStatsByUris(start, end, List.of(response.uri()))).thenReturn(List.of(response));
        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("uris", response.uri())
                        .content(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(response.app()), String.class))
                .andExpect(jsonPath("$[0].uri", is(response.uri()), String.class))
                .andExpect(jsonPath("$[0].hits", is(response.hits()), Integer.class));
        verify(endpointHitService, times(1)).getStatsByUris(start, end, List.of(response.uri()));
    }

    @Test
    void testGetStatsUnique() throws Exception {
        when(endpointHitService.getStatsUnique(start, end)).thenReturn(List.of(response));
        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("unique", "true")
                        .content(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(response.app()), String.class))
                .andExpect(jsonPath("$[0].uri", is(response.uri()), String.class))
                .andExpect(jsonPath("$[0].hits", is(response.hits()), Integer.class));
        verify(endpointHitService, times(1)).getStatsUnique(start, end);
    }

    @Test
    void testGetStatsUniqueByUris() throws Exception {
        when(endpointHitService.getStatsUniqueByUris(start, end, List.of(response.uri()))).thenReturn(List.of(response));
        mockMvc.perform(get("/stats")
                        .param("start", start)
                        .param("end", end)
                        .param("uris", response.uri())
                        .param("unique", "true")
                        .content(objectMapper.writeValueAsString(response))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].app", is(response.app()), String.class))
                .andExpect(jsonPath("$[0].uri", is(response.uri()), String.class))
                .andExpect(jsonPath("$[0].hits", is(response.hits()), Integer.class));
        verify(endpointHitService, times(1)).getStatsUniqueByUris(start, end, List.of(response.uri()));
    }
}
