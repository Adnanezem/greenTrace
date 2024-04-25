package com.greentracer.app.dao;

import com.greentracer.app.models.Historique;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class HistoriqueDaoTest {



    private Historique historique;

    @Autowired
    private HistoriqueDao historiqueDao;
/*
    @BeforeEach
    public void setUp() {
        historique = new Historique();
        historique.setidp("test");
        historique.sethistorique(60.0f);
        historiqueDao.create(historique);
    }

    //@AfterEach // a remmetre quand la requete sera correcte (DELETE)
    public void tearDown() {
        historiqueDao.delete(historique);
    }


    @Test
    public void testGetById() {
        Historique h = historiqueDao.getById("test");
        assertEquals(h.getidp(), historique.getidp());
        assertEquals(h.gethistorique(), historique.gethistorique());
    }

    @Test
    public void testCreate() {
        Historique h = new Historique();
        h.setidp("test2");
        h.sethistorique(0.0f);
        assertTrue(historiqueDao.create(h));
        assertEquals(historiqueDao.getById("test2").getidp(), "test2");
        assertEquals(historiqueDao.getById("test2").gethistorique(), 0.0f);
    }

    @Test
    public void testUpdate() {
        historique.sethistorique(0.0f);
        historique.setidp("testUpdated");
        assertTrue(historiqueDao.update(historique));
        assertEquals(historiqueDao.getById("testUpdated").gethistorique(), 0.0f);
        assertEquals(historiqueDao.getById("testUpdated").getidp(), "testUpdated");
    }

    @Test
    public void testGetAll() {
        assertNotNull(historiqueDao.getAll());
    }

*/

}