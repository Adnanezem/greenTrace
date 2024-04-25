package com.greentracer.app.dao;

import com.greentracer.app.models.Journee;
import com.greentracer.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JourneeDaoTest {

   /*private Journee journee;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JourneeDao journeeDao;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        user.setLname("test");
        user.setFname("test");
        userDao.create(user);

        journee = new Journee();
        journee.setpatientId("test");
        journee.setdate(Date.valueOf("2021-01-01"));
        journee.setresultat(0.0f);
        journeeDao.create(journee);
    }

    @AfterEach // a remmetre quand la requete sera correcte (DELETE)
    public void tearDown() {
        //journeeDao.delete(journee);
        userDao.delete(userDao.getById("test"));
    }


    @Test
    public void testGetById() {
        Journee j = journeeDao.getById("test");
        assertEquals(j.getid(), journee.getid());
        assertEquals(j.getpatientId(), journee.getpatientId());
        assertEquals(j.getdate(), journee.getdate());
        assertEquals(j.getresultat(), journee.getresultat());
    }

    @Test
    public void testGetAll() {
        assertNotNull(journeeDao.getAll());
    }

    @Test
    public void testUpdate() {
        journee.setresultat(0.0f);
        journee.setpatientId("testUpdated");
        assertTrue(journeeDao.update(journee));
        assertEquals(journeeDao.getById("test").getresultat(), 0.0f);
        assertEquals(journeeDao.getById("test").getpatientId(), "testUpdated");
        assertEquals(journeeDao.getById("test").getdate(), Date.valueOf("2021-01-01"));
        assertEquals(journeeDao.getById("test").getid(), 1);
    }

    @Test
    public void testDelete() {
        assertTrue(journeeDao.delete(journee));
        assertNull(journeeDao.getById("test"));
    }

    @Test
    public void testCreate() {
        Journee j = new Journee();
        j.setpatientId("test2");
        j.setdate(Date.valueOf("2021-01-01"));
        j.setresultat(0.0f);
        assertTrue(journeeDao.create(j));
        assertEquals(journeeDao.getById("test2").getpatientId(), "test2");
        assertEquals(journeeDao.getById("test2").getdate(), Date.valueOf("2021-01-01"));
        assertEquals(journeeDao.getById("test2").getresultat(), 0.0f);
    }

    @Test
    public void testGetByDate() {
        Journee j = journeeDao.getByDate("test", Date.valueOf("2021-01-01"));
        assertEquals(j.getid(), journee.getid());
        assertEquals(j.getpatientId(), journee.getpatientId());
        assertEquals(j.getdate(), journee.getdate());
        assertEquals(j.getresultat(), journee.getresultat());
    }

*/

}