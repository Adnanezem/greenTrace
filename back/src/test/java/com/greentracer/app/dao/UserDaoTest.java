package com.greentracer.app.dao;

import com.greentracer.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserDaoTest {

    private User user;

    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin("test");
        user.setPassword("test");
        user.setLname("test");
        user.setFname("test");
        userDao.create(user);
    }

    @AfterEach
    public void tearDown() {
        userDao.delete(user);
    }



    @Test
    void testGetById() {
        User u = userDao.getById("test");
        assertEquals(u.getLogin(), user.getLogin());
        assertEquals(u.getPassword(), user.getPassword());
        assertEquals(u.getLname(), user.getLname());
        assertEquals(u.getFname(), user.getFname());
    }

    @Test
    void getById_withNullId_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userDao.getById(null));
    }

    @Test
    void testGetAll() {
        assertNotNull(userDao.getAll());
    }

    @Test
    void testUpdate() {
        user.setFname("FnameUpdated");
        assertTrue(userDao.update(user));
        assertEquals("FnameUpdated", userDao.getById("test").getFname());
    }

    @Test
    void testUpdateUserFailure() {
        // Arrange
        User user = new User();
        user.setLogin("nonexistent");
        user.setFname("Test");
        user.setLname("User");
        user.setPassword("password");
        // Act
        Boolean result = userDao.update(user);
        // Assert
        assertFalse(result);
        userDao.delete(user);
    }

    @Test
    void testDelete() {
        assertTrue(userDao.delete(user));
        assertThrows(IllegalArgumentException.class, () -> userDao.getById("test"));
    }

   @Test
    void testCreate() {
        User u = new User("test2", "test2", "test2", "test2");
        assertTrue(userDao.create(u));
        assertEquals("test2", userDao.getById("test2").getFname());
        assertEquals("test2", userDao.getById("test2").getLname());
        assertEquals("test2", userDao.getById("test2").getPassword());
        assertEquals("test2", userDao.getById("test2").getLogin());

        userDao.delete(u);
    }


    @Test
    void testCreateUserFailure() {
        // Arrange
        //Password not set, so the user is invalid
        User user = new User();
        user.setLogin("nonexistent");
        user.setFname("Test");
        user.setLname("User");
        // Act
        Boolean result = userDao.create(user);
        // Assert
        assertFalse(result);
    }

}