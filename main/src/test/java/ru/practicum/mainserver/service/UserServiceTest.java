package ru.practicum.mainserver.service;

import ru.practicum.mainserver.dto.user.NewUserRequest;
import ru.practicum.mainserver.dto.user.UserDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainserver.factory.ModelFactory;
import ru.practicum.mainserver.service.entity.User;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class UserServiceTest {
    private final EntityManager em;
    private final UserService userService;
    private UserDto result;

    @Test
    @Rollback
    void testGetAll() {
        List<Long> userIds = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            NewUserRequest newUserRequest = new NewUserRequest();
            newUserRequest.setName("user" + i);
            newUserRequest.setEmail(newUserRequest.getName() + "@example.com");
            result = userService.create(newUserRequest);
            userIds.add(result.getId());
        }
        List<UserDto> resultList = userService.getAll(List.of(userIds.getFirst()), 0, 5);
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id IN :ids", User.class);
        List<User> userList = query.setParameter("ids", List.of(userIds.getFirst()))
                .getResultList();

        assertThat(resultList.size(), equalTo(1));
        assertThat(userList.size(), equalTo(1));

        resultList = userService.getAll(List.of(userIds.getFirst(), userIds.getLast()), 0, 5);
        query = em.createQuery("SELECT u FROM User u WHERE u.id IN :ids", User.class);
        userList = query.setParameter("ids", List.of(userIds.getFirst(), userIds.getLast()))
                .getResultList();

        assertThat(resultList.size(), equalTo(2));
        assertThat(userList.size(), equalTo(2));

        resultList = userService.getAll(userIds, 0, 2);
        query = em.createQuery("SELECT u FROM User u WHERE u.id IN :ids", User.class);
        userList = query.setParameter("ids", userIds)
                .getResultList();

        assertThat(resultList.size(), equalTo(2));
        assertThat(userList.size(), equalTo(3));

        resultList = userService.getAll(userIds, 2, 2);
        assertThat(resultList.size(), equalTo(1));
    }

    @Test
    @Rollback
    void testCreate() {
        NewUserRequest newUserRequest = ModelFactory.createNewUserRequest();
        result = userService.create(newUserRequest);
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
        User user = query.setParameter("id", result.getId())
                .getSingleResult();
        assertThat(user.getId(), notNullValue());
        assertThat(user.getName(), equalTo(result.getName()));
        assertThat(user.getEmail(), equalTo(result.getEmail()));

        newUserRequest.setEmail(result.getEmail());
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userService.create(newUserRequest));
    }

    @Test
    @Rollback
    void testDelete() {
        NewUserRequest newUserRequest = ModelFactory.createNewUserRequest();
        result = userService.create(newUserRequest);
        userService.delete(result.getId());
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);

        Assertions.assertThrows(NoResultException.class, () -> query.setParameter("id", result.getId())
                .getSingleResult());
    }
}
