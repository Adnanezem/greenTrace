package com.greentracer.app.dao;

import com.greentracer.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDaoTest {

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
    public void testGetById() {
        User u = userDao.getById("test");
        assertEquals(u.getLogin(), user.getLogin());
        assertEquals(u.getPassword(), user.getPassword());
        assertEquals(u.getLname(), user.getLname());
        assertEquals(u.getFname(), user.getFname());
    }

    @Test
    public void testGetAll() {
        assertNotNull(userDao.getAll());
    }

    @Test
    public void testUpdate() {
        user.setFname("testUpdated");
        assertTrue(userDao.update(user));
        //assertEquals(userDao.getById("test").getFname(), "testUpdated");
    }

    @Test
    public void testDelete() {
        assertTrue(userDao.delete(user));
        assertThrows(IllegalArgumentException.class, () -> userDao.getById("test"));
    }

   /*@Test
    public void testCreate() {
        User u = new User("test2", "test2", "test2", "test2");
        assertTrue(userDao.create(u));
        assertEquals(userDao.getById("test2").getFname(), "test2");
        assertEquals(userDao.getById("test2").getLname(), "test2");
        assertEquals(userDao.getById("test2").getPassword(), "test2");
        assertEquals(userDao.getById("test2").getLogin(), "test2");
    }*/


}