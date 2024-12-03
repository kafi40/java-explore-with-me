package ru.practicum.mainserver.service;

import ru.practicum.mainserver.dto.category.CategoryDto;
import ru.practicum.mainserver.dto.category.NewCategoryDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.exception.NotFountException;
import ru.practicum.mainserver.factory.ModelFactory;
import ru.practicum.mainserver.service.entity.Category;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class CategoryServiceTest {
    private final EntityManager em;
    private final CategoryService categoryService;
    private CategoryDto result;
    private final Long notExistsId = 99999L;



    @Test
    @Rollback
    void testGet() {
        NewCategoryDto newCategoryDto = ModelFactory.createNewCategoryDto();
        result = categoryService.create(newCategoryDto);
        result = categoryService.get(result.getId());
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category AS c WHERE  c.id = :id", Category.class);
        Category category = query.setParameter("id", result.getId())
                .getSingleResult();
        assertThat(category.getId(), notNullValue());
        assertThat(category.getName(), equalTo(result.getName()));
        Assertions.assertThrows(NotFountException.class, () -> categoryService.get(notExistsId));
    }

    @Test
    @Rollback
    void testGetAll() {
        for (int i = 1; i < 11; i++) {
            NewCategoryDto newCategoryDto = new NewCategoryDto();
            newCategoryDto.setName("Test Category " + i);
            categoryService.create(newCategoryDto);
        }
        List<CategoryDto> resultList = categoryService.getAll(0, 9);
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category AS c", Category.class);
        List<Category> categoryList = query
                .setMaxResults(9)
                .getResultList();

        assertThat(resultList.size(), equalTo(9));
        assertThat(categoryList.size(), equalTo(9));
    }

    @Test
    @Rollback
    void testCreate() {
        NewCategoryDto newCategoryDto = ModelFactory.createNewCategoryDto();
        result = categoryService.create(newCategoryDto);
        TypedQuery<Category> query = em.createQuery("SELECT c FROM Category AS c WHERE  c.id = :id", Category.class);
        Category category = query.setParameter("id", result.getId())
                .getSingleResult();
        assertThat(category.getId(), notNullValue());
        assertThat(category.getName(), equalTo(result.getName()));
    }

    @Test
    @Rollback
    void testUpdate() {
        NewCategoryDto newCategoryDto = ModelFactory.createNewCategoryDto();
        result = categoryService.create(newCategoryDto);
        CategoryDto categoryDto = categoryService.get(result.getId());
        categoryDto.setName("Updated Category");
        categoryService.update(result.getId(), categoryDto);
        result = categoryService.get(result.getId());
        assertThat(result.getName(), equalTo(categoryDto.getName()));
    }

    @Test
    @Rollback
    void testDelete() {
        NewCategoryDto newCategoryDto = ModelFactory.createNewCategoryDto();
        result = categoryService.create(newCategoryDto);
        categoryService.delete(result.getId());
        Assertions.assertThrows(NotFountException.class, () -> categoryService.get(result.getId()));
    }
}
