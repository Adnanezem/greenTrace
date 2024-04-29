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
public class HistoriqueDaoTest {



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
    public void tearDown() {
        historiqueDao.delete(historique);
        userDao.delete(user); // delete aussi les journee crée avec l'ID utilisateur correspondant (ON CASCADE).
    }


    @Test
    public void testGetById() {
        Historique h = historiqueDao.getById("testHist");
        assertEquals(h.getidp(), historique.getidp());
        assertEquals(h.gethistorique(), 46); // => hist == aux 7 dernieres journee (calc auto à la création). Ici 46 avec l'init
    }

    @Test
    public void getById_whenNoHistoriqueFound_returnsNull() {
        Historique h = historiqueDao.getById("nonexistent");
        assertNull(h);
    }

    @Test
    public void testUpdate() {
        historique.sethistorique(0.0f);
        assertTrue(historiqueDao.update(historique));
        assertEquals(historiqueDao.getById("testHist").gethistorique(), 0.0f);
        assertEquals(historiqueDao.getById("testHist").getidp(), "testHist");
    }

    @Test
    public void testGetAll() {
        assertNotNull(historiqueDao.getAll());
    }



}