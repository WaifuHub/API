package models

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.whub.models.Comment
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class CommentTest {
    private val MAPPER: ObjectMapper = jacksonObjectMapper()
    private val TEST_COMMENT_ID: String = "test_comment_id"
    private val TEST_HUB_ID: String = "test_hub_id"
    private val TEST_USER_ID: String = "test_user_id"
    private val TEST_LIKE_COUNT: Int = 0
    private val TEST_COMMENT_TEXT = "test_comment_text"
    private val TEST_DATE_POSTED = Date()
    private val TEST_COMMENT =
        Comment(TEST_COMMENT_ID, TEST_HUB_ID, TEST_USER_ID, TEST_LIKE_COUNT, TEST_COMMENT_TEXT, TEST_DATE_POSTED)
    private val TEST_SERIALIZED_COMMENT =
        String.format(
            "{\"commentId\":\"%s\",\"hubId\":\"%s\",\"userId\":\"%s\"," +
                    "\"likeCount\":%d,\"commentText\":\"%s\",\"datePosted\":%tQ}",
            TEST_COMMENT_ID,
            TEST_HUB_ID,
            TEST_USER_ID,
            TEST_LIKE_COUNT,
            TEST_COMMENT_TEXT,
            TEST_DATE_POSTED
        )

    @Test
    fun `when given comment serialization succeeds`() {
        val serializedComment = MAPPER.writeValueAsString(TEST_COMMENT)
        assertEquals(serializedComment, TEST_SERIALIZED_COMMENT)
    }

    @Test
    fun `when given correct comment json deserialization succeeds`() {
        val comment: Comment = MAPPER.readValue(TEST_SERIALIZED_COMMENT)
        assertEquals(comment, TEST_COMMENT)
    }
}