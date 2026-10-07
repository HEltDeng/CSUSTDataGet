package com.dcelysia.csust_spider.core

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class CampusCardAuthConfigTest {
    @Before
    fun setUp() = CampusCardAuthConfig.clear()

    @After
    fun tearDown() = CampusCardAuthConfig.clear()

    @Test
    fun missingConfigurationFailsExplicitly() {
        assertThrows(IllegalStateException::class.java) { CampusCardAuthConfig.requireBasicAuth() }
        assertThrows(IllegalStateException::class.java) { CampusCardAuthConfig.requireChargeAuth() }
    }

    @Test
    fun suppliedParametersPassThroughUnchanged() {
        CampusCardAuthConfig.configure("synthetic-basic-fixture", "synthetic-charge-fixture")
        assertEquals("synthetic-basic-fixture", CampusCardAuthConfig.requireBasicAuth())
        assertEquals("synthetic-charge-fixture", CampusCardAuthConfig.requireChargeAuth())
    }

    @Test
    fun invalidReplacementDoesNotDiscardPreviousConfiguration() {
        CampusCardAuthConfig.configure("synthetic-basic-fixture", "synthetic-charge-fixture")
        assertThrows(IllegalArgumentException::class.java) {
            CampusCardAuthConfig.configure("synthetic-new-fixture", " ")
        }
        assertEquals("synthetic-basic-fixture", CampusCardAuthConfig.requireBasicAuth())
        assertEquals("synthetic-charge-fixture", CampusCardAuthConfig.requireChargeAuth())
    }

    @Test
    fun invalidHeaderErrorDoesNotExposeInput() {
        val invalid = "synthetic-private-fixture\r\nInvalid-Header: value"
        val error = assertThrows(IllegalArgumentException::class.java) {
            CampusCardAuthConfig.configure(invalid, "synthetic-charge-fixture")
        }
        assertFalse(error.message.orEmpty().contains("synthetic-private-fixture"))
    }

    @Test
    fun clearingConfigurationRemovesAccess() {
        CampusCardAuthConfig.configure("synthetic-basic-fixture", "synthetic-charge-fixture")
        CampusCardAuthConfig.clear()
        assertThrows(IllegalStateException::class.java) { CampusCardAuthConfig.requireChargeAuth() }
    }
}
