package com.whub.dao

import com.whub.models.Comment
import com.whub.models.Hub
import com.whub.models.User
import org.ehcache.CacheManagerBuilder
import org.ehcache.PersistentCacheManager
import org.ehcache.config.CacheConfigurationBuilder
import org.ehcache.config.ResourcePoolsBuilder
import org.ehcache.config.persistence.CacheManagerPersistenceConfiguration
import org.ehcache.config.units.EntryUnit
import org.ehcache.config.units.MemoryUnit
import java.io.File

class EntryDaoCache(private val delegate: EntryDao, storagePathHubs: File, storagePathComments: File) : DaoFacade {
    private val hubsCacheProvider: PersistentCacheManager =
        CacheManagerBuilder.newCacheManagerBuilder().with(CacheManagerPersistenceConfiguration(storagePathHubs))
            .withCache(
                "HubCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder<String, Hub>()
                    .withResourcePools(
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                            .heap(1000, EntryUnit.ENTRIES)
                            .offheap(10, MemoryUnit.MB)
                            .disk(100, MemoryUnit.MB, true)
                    )
                    .buildConfig(String::class.javaObjectType, Hub::class.java)
            )
            .build(true)
    private val commentsCacheProvider: PersistentCacheManager =
        CacheManagerBuilder.newCacheManagerBuilder().with(CacheManagerPersistenceConfiguration(storagePathComments))
            .withCache(
                "CommentCache",
                CacheConfigurationBuilder.newCacheConfigurationBuilder<String, Comment>()
                    .withResourcePools(
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                            .heap(1000, EntryUnit.ENTRIES)
                            .offheap(10, MemoryUnit.MB)
                            .disk(100, MemoryUnit.MB, true)
                    )
                    .buildConfig(String::class.javaObjectType, Comment::class.java)
            )
            .build(true)
    private val hubsCache = hubsCacheProvider.getCache("HubCache", String::class.javaObjectType, Hub::class.java)
    private val commentsCache =
        commentsCacheProvider.getCache("CommentCache", String::class.javaObjectType, Comment::class.java)

    override fun init() {
        delegate.init()
    }

    override fun createUser(user: User): Int {
        return delegate.createUser(user)
    }

    override fun getUserByEmail(email: String): User? {
        return delegate.getUserByEmail(email)
    }

    override fun getUserByUid(uid: String): User? {
        return delegate.getUserByUid(uid)
    }

    override fun deleteUser(uid: String): Int {
        return delegate.deleteUser(uid)
    }

    override fun createComment(comment: Comment): Int {
        val statusCode = delegate.createComment(comment)
        return if (statusCode == 0) {
            commentsCache.put(comment.commentId, comment)
            0
        } else {
            -1
        }
    }

    override fun getCommentByCommentId(commentId: String): Comment? {
        return commentsCache.get(commentId) ?: return delegate.getCommentByCommentId(commentId)
    }

    override fun deleteComment(commentId: String): Int {
        return try {
            delegate.deleteComment(commentId)
            commentsCache.remove(commentId)
            0
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    override fun createHub(hub: Hub): Int {
        val statusCode = delegate.createHub(hub)
        return if (statusCode == 0) {
            hubsCache.put(hub.hubId, hub)
            0
        } else {
            -1
        }
    }

    override fun getHubById(id: String): Hub? {
        return hubsCache.get(id) ?: return delegate.getHubById(id)
    }

    override fun deleteHub(id: String): Int {
        return try {
            delegate.deleteHub(id)
            hubsCache.remove(id)
            0
        } catch (e: IllegalArgumentException) {
            -1
        }
    }

    override fun close() {
        try {
            delegate.close()
        } finally {
            commentsCacheProvider.close()
            hubsCacheProvider.close()
        }
    }

}