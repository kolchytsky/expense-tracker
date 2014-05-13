package com.coldenergia.expensetracker.repository;

import com.coldenergia.expensetracker.builder.UnitBuilder;
import com.coldenergia.expensetracker.domain.Unit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * User: coldenergia
 * Date: 5/3/14
 * Time: 9:32 PM
 */
public class UnitRepositoryIntegrationTest extends RepositoryIntegrationTest {

    @Autowired
    private UnitRepository unitRepository;

    @Test
    public void shouldSaveUnit() {
        Unit unit = new UnitBuilder().build();
        Unit retrievedUnit = unitRepository.save(unit);
        assertNotNull(retrievedUnit);
        assertEquals(unit, retrievedUnit);
    }

}
