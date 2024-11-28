package ru.practicum.mainserver.controller;

import com.example.maincommon.dto.category.CategoryDto;
import com.example.maincommon.dto.category.NewCategoryDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.mainserver.factory.ModelFactory;
import ru.practicum.mainserver.service.CategoryService;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class)
public class CategoryControllerTest {
    @MockBean
    private CategoryService categoryService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private CategoryDto categoryDto;
    private NewCategoryDto newCategoryDto;

    @BeforeEach
    void setUp() {
        categoryDto = ModelFactory.createCategoryDto();
        newCategoryDto = ModelFactory.createNewCategoryDto();
    }

    @Test
    void testGet() throws Exception {
        when(categoryService.get(categoryDto.getId())).thenReturn(categoryDto);
        mockMvc.perform(get("/categories/" + categoryDto.getId())
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(categoryDto.getName()), String.class));

        verify(categoryService, times(1)).get(categoryDto.getId());
    }

    @Test
    void testGetAllForWithParamFrom0With10() throws Exception {
        int from = 0;
        int size = 10;
        when(categoryService.getAll(from, size)).thenReturn(ModelFactory.CreateCategoryDtoList(size));
        mockMvc.perform(get("/categories")
                        .param("from", String.valueOf(from))
                        .param("size", String.valueOf(size))
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(size)))
                .andExpect(jsonPath("$[0].id", is(1L), Long.class))
                .andExpect(jsonPath("$[0].name", is("Category 1"), String.class))
                .andExpect(jsonPath("$[9].id", is(10L), Long.class))
                .andExpect(jsonPath("$[9].name", is("Category 10"), String.class));

        verify(categoryService, times(1)).getAll(from, size);
    }

    @Test
    void testCreate() throws Exception {
        when(categoryService.create(newCategoryDto)).thenReturn(categoryDto);

        mockMvc.perform(post("/admin/categories")
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(categoryDto.getName()), String.class));

        verify(categoryService, times(1)).create(newCategoryDto);
    }

    @Test
    void testUpdate() throws Exception {
        when(categoryService.update(categoryDto)).thenReturn(categoryDto);

        mockMvc.perform(patch("/admin/categories/" + categoryDto.getId())
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(categoryDto.getId()), Long.class))
                .andExpect(jsonPath("$.name", is(categoryDto.getName()), String.class));

        verify(categoryService, times(1)).update(categoryDto);
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/admin/categories/" + categoryDto.getId())
                        .content(objectMapper.writeValueAsString(categoryDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).delete(categoryDto.getId());
    }
}
