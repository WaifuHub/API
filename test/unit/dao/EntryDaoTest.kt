package unit.dao

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.model.*
import com.whub.dao.EntryDao
import com.whub.models.Comment
import com.whub.models.Hub
import com.whub.models.User
import org.joda.time.format.ISODateTimeFormat.date
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.isA
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.stubbing.Answer
import kotlin.test.assertEquals


class EntryDaoTest {
    @Mock
    private val MOCOK_DB_CLIENT: AmazonDynamoDB = Mockito.mock(AmazonDynamoDB::class.java)
    private val TEST_ID: String = "test_id"
    private val TEST_EMAIL: String = "test_email@test.test"
    private val TEST_DISPLAY_NAME: String = "test_display_name"
    private val TEST_PASSWORD_HASH: String = "test_pass_hash"
    private val TEST_URL: String = "https://test_uri.png"
    private val TEST_COMMENT_TEXT: String = "test_text"
    private val TEST_LIKE_COUNT: String = "1"
    private val TEST_DATE: String = date().toString()
    private val TEST_HUB_NAME: String = "test_name"
    private val TEST_HUB_DESCRIPTION = "test_description"
    private val TEST_USER: User = User(TEST_ID, TEST_EMAIL, TEST_DISPLAY_NAME, TEST_PASSWORD_HASH, TEST_URL)
    private val TEST_HUB: Hub = Hub(TEST_ID, TEST_HUB_NAME, TEST_HUB_DESCRIPTION, TEST_URL)
    private val TEST_COMMENT: Comment =
        Comment(TEST_ID, TEST_ID, TEST_ID, TEST_LIKE_COUNT, TEST_COMMENT_TEXT, TEST_DATE)
    private val TEST_ITEM_RESULT_USER = mapOf(
        "userId" to AttributeValue(TEST_ID),
        "email" to AttributeValue(TEST_EMAIL),
        "displayName" to AttributeValue(TEST_DISPLAY_NAME),
        "passwordHash" to AttributeValue(TEST_PASSWORD_HASH),
        "profileImageUrl" to AttributeValue(TEST_URL)
    )
    private val TEST_ITEM_RESULT_COMMENT = mapOf(
        "commentId" to AttributeValue(TEST_ID),
        "hubId" to AttributeValue(TEST_ID),
        "userId" to AttributeValue(TEST_ID),
        "likeCount" to AttributeValue(TEST_LIKE_COUNT),
        "commentText" to AttributeValue(TEST_COMMENT_TEXT),
        "datePosted" to AttributeValue(TEST_DATE)
    )
    private val TEST_ITEM_RESULT_HUB = mapOf(
        "hubId" to AttributeValue(TEST_ID),
        "hubName" to AttributeValue(TEST_HUB_NAME),
        "hubDescription" to AttributeValue(TEST_HUB_DESCRIPTION),
        "hubImageUrl" to AttributeValue(TEST_URL)
    )


    @Test
    fun `when given user save succeeds`() {
        Mockito.`when`(MOCOK_DB_CLIENT.putItem(ArgumentMatchers.any())).thenAnswer {
            val result = PutItemResult()
            result
        }
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val resultCode: Int = dao.createUser(TEST_USER)
        assertEquals(resultCode, 0)
    }

    @Test
    fun `when given email user is retrieved`() {
        Mockito.`when`(MOCOK_DB_CLIENT.getItem(isA(GetItemRequest::class.java))).thenAnswer(Answer {
            val result = GetItemResult()
            result.item = TEST_ITEM_RESULT_USER
            result
        })
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val response = dao.getUserByEmail(TEST_EMAIL)
        assertEquals(response, TEST_USER)
    }

    @Test
    fun `when given uid user is retrieved`() {
        Mockito.`when`(MOCOK_DB_CLIENT.getItem(isA(GetItemRequest::class.java))).thenAnswer(Answer {
            val result = GetItemResult()
            result.item = TEST_ITEM_RESULT_USER
            result
        })
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val response = dao.getUserByUid(TEST_ID)
        assertEquals(response, TEST_USER)
    }

    @Test
    fun `when delete by uid delete succeeds`() {
        Mockito.`when`(MOCOK_DB_CLIENT.deleteItem(isA(DeleteItemRequest::class.java))).thenAnswer {
            val result = DeleteItemResult()
            result
        }
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val response = dao.deleteUser(TEST_ID)
        assertEquals(response, 0)
    }

    @Test
    fun `when given comment save succeeds`() {
        Mockito.`when`(MOCOK_DB_CLIENT.putItem(ArgumentMatchers.any())).thenAnswer {
            val result = PutItemResult()
            result
        }
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val response = dao.createComment(TEST_COMMENT)
        assertEquals(response, 0)
    }

    @Test
    fun `when given commentId comment is retrieved`() {
        Mockito.`when`(MOCOK_DB_CLIENT.getItem(isA(GetItemRequest::class.java))).thenAnswer(Answer {
            val result = GetItemResult()
            result.item = TEST_ITEM_RESULT_COMMENT
            result
        })
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val response = dao.getCommentByCommentId(TEST_ID)
        assertEquals(response, TEST_COMMENT)
    }

    @Test
    fun `when delete by commentId delete succeeds`() {
        Mockito.`when`(MOCOK_DB_CLIENT.deleteItem(isA(DeleteItemRequest::class.java))).thenAnswer {
            val result = DeleteItemResult()
            result
        }
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val response = dao.deleteComment(TEST_ID)
        assertEquals(response, 0)
    }

    @Test
    fun `when given hub save succeeds`() {
        Mockito.`when`(MOCOK_DB_CLIENT.putItem(ArgumentMatchers.any())).thenAnswer {
            val result = PutItemResult()
            result
        }
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val resultCode: Int = dao.createComment(TEST_COMMENT)
        assertEquals(resultCode, 0)
    }

    @Test
    fun `when given hubId hub is retrieved`() {
        Mockito.`when`(MOCOK_DB_CLIENT.getItem(isA(GetItemRequest::class.java))).thenAnswer(Answer {
            val result = GetItemResult()
            result.item = TEST_ITEM_RESULT_HUB
            result
        })
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val response = dao.getHubById(TEST_ID)
        assertEquals(response, TEST_HUB)
    }

    @Test
    fun `when delete by hubId delete succeeds`() {
        Mockito.`when`(MOCOK_DB_CLIENT.deleteItem(isA(DeleteItemRequest::class.java))).thenAnswer {
            val result = DeleteItemResult()
            result
        }
        val dao: EntryDao = EntryDao(MOCOK_DB_CLIENT)
        val response = dao.deleteHub(TEST_ID)
        assertEquals(response, 0)
    }
}