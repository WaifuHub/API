package test.models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.whub.models.User
import org.junit.Assert.assertEquals
import org.junit.Test

class UserTest {
    private val MAPPER: ObjectMapper = jacksonObjectMapper()
    private val TEST_ID: String = "test_id"
    private val TEST_EMAIL: String = "test_email@test.test"
    private val TEST_DISPLAY_NAME: String = "test_display_name"
    private val TEST_PASSWORD_HASH: String = "test_pass_hash"
    private val TEST_USER: User = User(TEST_ID, TEST_EMAIL, TEST_DISPLAY_NAME, TEST_PASSWORD_HASH)
    private val TEST_SERIALIZED_USER = String.format(
        "{\"userId\":\"%s\",\"email\":\"%s\",\"displayName\":\"%s\"," +
                "\"passwordHash\":\"%s\"}", TEST_ID, TEST_EMAIL, TEST_DISPLAY_NAME, TEST_PASSWORD_HASH
    )

    @Test
    fun `when given user serialization succeeds`() {
        val serializedUser: String = MAPPER.writeValueAsString(TEST_USER)
        assertEquals(serializedUser, TEST_SERIALIZED_USER)
    }

    @Test
    fun `when given correct user json deserilization succeeds`() {
        val user: User = MAPPER.readValue(TEST_SERIALIZED_USER)
        assertEquals(user, TEST_USER)
    }
}