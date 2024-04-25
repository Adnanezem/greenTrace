package com.greentracer.app.mappers;

import com.greentracer.app.models.Journee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class JourneeMapperTest {

    @Mock
    private ResultSet resultSet;

    private RowMapper<Journee> journeeMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        journeeMapper = new JourneeMapper();
    }

    @Test
    void mapRow_shouldReturnJournee() throws SQLException {
        when(resultSet.getInt("idJournee")).thenReturn(1);
        when(resultSet.getString("idP")).thenReturn("testId");
        when(resultSet.getDate("Date")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        when(resultSet.getFloat("resultat")).thenReturn(1.0f);

        journeeMapper.mapRow(resultSet, 1);

        verify(resultSet, times(1)).getInt("idJournee");
        verify(resultSet, times(1)).getString("idP");
        verify(resultSet, times(1)).getDate("Date");
        verify(resultSet, times(1)).getFloat("resultat");
    }

    @Test
    void mapRow_shouldHandleSQLException() throws SQLException {
        when(resultSet.getInt("idJournee")).thenThrow(SQLException.class);

        try {
            journeeMapper.mapRow(resultSet, 1);
        } catch (SQLException ignored) {
        }

        verify(resultSet, times(1)).getInt("idJournee");
    }
}