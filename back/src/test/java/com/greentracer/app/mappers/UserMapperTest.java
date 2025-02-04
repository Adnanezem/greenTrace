package com.greentracer.app.mappers;

import com.greentracer.app.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserMapperTest {

    private ResultSet resultSetMock;
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        resultSetMock = Mockito.mock(ResultSet.class);
        userMapper = new UserMapper();
    }

    @Test
    void mapRow_validResultSet_returnsUser() throws SQLException {
        // Arrange
        when(resultSetMock.getString("login")).thenReturn("testLogin");
        when(resultSetMock.getString("nom")).thenReturn("testFname");
        when(resultSetMock.getString("prenom")).thenReturn("testLname");
        when(resultSetMock.getString("mdps")).thenReturn("testPassword");

        // Act
        User result = userMapper.mapRow(resultSetMock, 1);

        // Assert
        assertEquals("testLogin", result.getLogin());
        assertEquals("testFname", result.getFname());
        assertEquals("testLname", result.getLname());
        assertEquals("testPassword", result.getPassword());
    }
}