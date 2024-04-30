package com.greentracer.app.dao;

import com.greentracer.app.models.Historique;
import com.greentracer.app.models.Journee;
import com.greentracer.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

@SpringBootTest
@ActiveProfiles("test")
class HistoriqueDaoTest {



    private Historique historique;

    private User user;

    @Autowired
    private HistoriqueDao historiqueDao;

    @Autowired
    private JourneeDao journeeDao;

    @Autowired
    private UserDao userDao;

    //allows to test the create request too
    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin("testHist");
        user.setPassword("test");
        user.setLname("test");
        user.setFname("test");
        userDao.create(user);
        Journee j = new Journee(0, "testHist", new Date(System.currentTimeMillis()), 46);
        journeeDao.create(j);
        historique = new Historique();
        historique.setidp(user.getLogin());
        historique.sethistorique(60.0f);
        historiqueDao.create(historique);
    }

    @AfterEach
    void tearDown() {
        historiqueDao.delete(historique);
        userDao.delete(user); // delete aussi les journee crée avec l'ID utilisateur correspondant (ON CASCADE).
    }


    @Test
    void testGetById() {
        Historique h = historiqueDao.getById("testHist");
        assertEquals(h.getidp(), historique.getidp());
        assertEquals(46, h.gethistorique()); // => hist == aux 7 dernieres journee (calc auto à la création). Ici 46 avec l'init
    }

    @Test
    void getById_whenNoHistoriqueFound_returnsNull() {
        Historique h = historiqueDao.getById("nonexistent");
        assertNull(h);
    }

    @Test
    void testUpdate() {
        historique.sethistorique(0.0f);
        assertTrue(historiqueDao.update(historique));
        assertEquals(historiqueDao.getById("testHist").gethistorique(), 0.0f);
        assertEquals("testHist", historiqueDao.getById("testHist").getidp());
    }

    @Test
    void testGetAll() {
        assertNotNull(historiqueDao.getAll());
    }



}