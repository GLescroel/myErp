package com.dummy.myerp.consumer.db.helper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
public class ResultSetHelperTest {

    @Mock
    private ResultSet resultSet;

    @Test
    public void testGetInteger() throws SQLException {
        Mockito.when(resultSet.getInt(anyString())).thenReturn(1);
        Assert.assertEquals(java.util.Optional.of(1).get(), ResultSetHelper.getInteger(resultSet,"colonne_test"));
    }

    @Test
    public void testGetLong() throws SQLException {
        Mockito.when(resultSet.getLong(anyString())).thenReturn(Long.valueOf(1));
        Assert.assertEquals(Long.valueOf(1), ResultSetHelper.getLong(resultSet,"colonne_test"));
    }

    @Test
    public void testGetDate() throws SQLException {
        Mockito.when(resultSet.getDate(anyString())).thenReturn(Date.valueOf(LocalDate.now()));
        Assert.assertEquals(Date.valueOf(LocalDate.now()), ResultSetHelper.getDate(resultSet,"colonne_test"));
    }
}