package com.greentracer.app.dao;

import com.greentracer.app.models.Historique;
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
public class HistoriqueDaoTest {



    private Historique historique;

    private User user;

    @Autowired
    private HistoriqueDao historiqueDao;

    @Autowired
    private UserDao userDao;

    //allows to test the create request too
    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin("testToDelete");
        user.setPassword("test");
        user.setLname("test");
        user.setFname("test");
        userDao.create(user);
        historique = new Historique();
        historique.setidp(user.getLogin());
        historique.sethistorique(60.0f);
        historiqueDao.create(historique);
    }

    @AfterEach // a remmetre quand la requete sera correcte (DELETE)
    public void tearDown() {
        //historiqueDao.delete(historique);
        userDao.delete(user);
    }


    @Test
    public void testGetById() {
        Historique h = historiqueDao.getById("testToDelete");
        assertEquals(h.getidp(), historique.getidp());
        assertEquals(h.gethistorique(), historique.gethistorique());
    }

    @Test
    public void testUpdate() {
        historique.sethistorique(0.0f);
        assertTrue(historiqueDao.update(historique));
        assertEquals(historiqueDao.getById("testToDelete").gethistorique(), 0.0f);
        assertEquals(historiqueDao.getById("testToDelete").getidp(), "testToDelete");
    }

    @Test
    public void testGetAll() {
        assertNotNull(historiqueDao.getAll());
    }



}