package com.greentracer.app;

import com.greentracer.app.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
public class AppConfigTest {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private DataSource dataSource;

    @Test
    public void dataSourceTest() throws SQLException {
        assertNotNull(dataSource);
        assertNotNull(dataSource.getConnection());
        assertEquals(appConfig.dataSource().getClass(), dataSource.getClass());
    }

    @Test
    public void dataSourcePropertiesTest() throws SQLException {
        Connection connection = dataSource.getConnection();
        DatabaseMetaData metaData = connection.getMetaData();

        //assertEquals("jdbc:postgresql://localhost:5432/green_tracer", metaData.getURL());
        //assertEquals("test", metaData.getUserName());
    }
}