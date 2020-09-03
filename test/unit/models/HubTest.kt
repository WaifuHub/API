package unit.models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.whub.models.Hub
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HubTest {
    private val MAPPER: ObjectMapper = jacksonObjectMapper()
    private val TEST_ID: String = "test_id"
    private val TEST_NAME: String = "test_name"
    private val TEST_DESCRIPTION = "test_description"
    private val TEST_URL: String = "https://test_uri.png"
    private val TEST_HUB: Hub = Hub(TEST_ID, TEST_NAME, TEST_DESCRIPTION, TEST_URL)
    private val TEST_SERIALIZED_HUB = String.format(
        "{\"hubId\":\"%s\",\"hubName\":\"%s\",\"hubDescription\":\"%s\"," +
                "\"hubImageUrl\":\"%s\"}", TEST_ID, TEST_NAME, TEST_DESCRIPTION, TEST_URL
    )


    @Test
    fun `when given hub serialization succeeds`() {
        val serializedHub: String = MAPPER.writeValueAsString(TEST_HUB)
        assertEquals(serializedHub, TEST_SERIALIZED_HUB)
    }

    @Test
    fun `when given correct hub json deserialization succeeds`() {
        val hub: Hub = MAPPER.readValue(TEST_SERIALIZED_HUB)
        assertEquals(hub, TEST_HUB)
    }
}