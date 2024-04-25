package com.greentracer.app.mappers;

import com.greentracer.app.models.Historique;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

class HistoriqueMapperTest {

    @Mock
    private ResultSet resultSet;

    private RowMapper<Historique> historiqueMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        historiqueMapper = new HistoriqueMapper();
    }

    @Test
    void mapRow_shouldReturnHistorique() throws SQLException {
        when(resultSet.getInt("idHistr")).thenReturn(1);
        when(resultSet.getString("idP")).thenReturn("testId");
        when(resultSet.getFloat("historique")).thenReturn(1.0f);

        historiqueMapper.mapRow(resultSet, 1);

        verify(resultSet, times(1)).getInt("idHistr");
        verify(resultSet, times(1)).getString("idP");
        verify(resultSet, times(1)).getFloat("historique");
    }

    @Test
    void mapRow_shouldHandleSQLException() throws SQLException {
        when(resultSet.getInt("idHistr")).thenThrow(SQLException.class);

        try {
            historiqueMapper.mapRow(resultSet, 1);
        } catch (SQLException ignored) {
        }

        verify(resultSet, times(1)).getInt("idHistr");
    }
}