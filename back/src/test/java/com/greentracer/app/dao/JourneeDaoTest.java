package com.greentracer.app.dao;

import com.greentracer.app.models.Journee;
import com.greentracer.app.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JourneeDaoTest {

    private Journee journee;

    private User user;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JourneeDao journeeDao;

    @BeforeEach
    public void setUp() {
        user = new User();
        journee = new Journee();

        user.setLogin("test");
        user.setPassword("test");
        user.setLname("test");
        user.setFname("test");
        userDao.create(user);

        journee.setpatientId("test");
        journee.setdate(Date.valueOf("2021-01-01"));
        journee.setresultat(150.0f);
        journeeDao.create(journee);
    }

    @AfterEach // a remmetre quand la requete sera correcte (DELETE)
    public void tearDown() {
        journeeDao.delete(journee);
        userDao.delete(user);
    }

    @Test
    void testGetById() {
        Journee j = journeeDao.getById("test");
        journee.setid(journeeDao.getById("test").getid());
        assertEquals(j.getid(), journee.getid());
        assertEquals(j.getpatientId(), journee.getpatientId());
        assertEquals(j.getdate(), journee.getdate());
        assertEquals(j.getresultat(), journee.getresultat());
    }

    @Test
    void testGetAll() {

        assertNotNull(journeeDao.getAll());
        User userTest = new User();
        userTest.setLogin("test2");
        userTest.setPassword("test2");
        userTest.setLname("test2");
        userTest.setFname("test2");
        userDao.create(userTest);

        Journee journee2 = new Journee();
        journee2.setpatientId("test2");
        journee2.setdate(Date.valueOf("2021-01-01"));
        journee2.setresultat(150.0f);
        journeeDao.create(journee2);

        assertEquals(2, journeeDao.getAll().size());
        userDao.delete(userTest);
    }

    @Test
    void testUpdate() {
        journee.setresultat(0.0f);
        journee.setdate(Date.valueOf("2024-04-26"));
        assertTrue(journeeDao.update(journee));
        assertEquals(0.0f, journeeDao.getById("test").getresultat());
        assertEquals(journeeDao.getById("test").getdate(), Date.valueOf("2024-04-26"));
    }

    @Test
    void testDelete() {
        assertTrue(journeeDao.delete(journee));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            journeeDao.getById("test");
        });

        String expectedMessage = "Aucune journée trouvée avec l'utilisateur spécifié.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreate() {
        User userTest = new User();
        userTest.setLogin("test2");
        userTest.setPassword("test2");
        userTest.setLname("test2");
        userTest.setFname("test2");
        userDao.create(userTest);

        Journee journee = new Journee();
        journee.setpatientId("test2");
        journee.setdate(Date.valueOf("2024-01-01"));
        journee.setresultat(150.0f);
        assertTrue(journeeDao.create(journee));

        // supression a la fin du test
        userDao.delete(userTest);

    }

    @Test
    void testGetByDate() {
        List<Journee> j = journeeDao.getByDate("test", Date.valueOf("2021-01-01"));
        assertEquals(j.get(0).getpatientId(), journee.getpatientId());
        assertEquals(j.get(0).getdate(), journee.getdate());
        assertEquals(j.get(0).getresultat(), journee.getresultat());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            journeeDao.getByDate("nonexistent", Date.valueOf("2022-02-02"));
        });

        String expectedMessage = "Aucune journée trouvée avec l'utilisateur et la date spécifiée.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getById_whenNoJourneeFound_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            journeeDao.getById("nonexistent");
        });

        String expectedMessage = "Aucune journée trouvée avec l'utilisateur spécifié.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getByDate_whenNoJourneeFound_throwsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            journeeDao.getByDate("nonexistent", Date.valueOf("2022-02-02"));
        });

        String expectedMessage = "Aucune journée trouvée avec l'utilisateur et la date spécifiée.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}