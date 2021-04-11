package com.peshale.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.utils.AndroidUtils

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {

    private val currentUser = "Test User"

    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKE_BY_ME} BOOLEAN DEFAULT 0,
            ${PostColumns.COLUMN_NUMBER_OF_LIKES} INTEGER DEFAULT 0,
            ${PostColumns.COLUMN_NUMBER_OF_SHARE} INTEGER DEFAULT 0,
            ${PostColumns.COLUMN_NUMBER_OF_VIEWS} INTEGER DEFAULT 0,
            ${PostColumns.COLUMN_VIDEO} TEXT DEFAULT ""
        );
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKE_BY_ME = "likedByMe"
        const val COLUMN_NUMBER_OF_LIKES = "likes"
        const val COLUMN_NUMBER_OF_SHARE = "shares"
        const val COLUMN_NUMBER_OF_VIEWS = "views"
        const val COLUMN_VIDEO = "video"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKE_BY_ME,
            COLUMN_NUMBER_OF_LIKES,
            COLUMN_NUMBER_OF_SHARE,
            COLUMN_NUMBER_OF_VIEWS,
            COLUMN_VIDEO
        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun addPost(post: Post): Post {
        val values = ContentValues().apply {
            if (post.id != 0L) {
                put(PostColumns.COLUMN_ID, post.id)
            }
            put(PostColumns.COLUMN_AUTHOR, currentUser)
            put(PostColumns.COLUMN_CONTENT, post.content)
            put(PostColumns.COLUMN_PUBLISHED, AndroidUtils.addLocalDataTime())
            put(PostColumns.COLUMN_VIDEO, post.video)
        }
        val id = db.replace(PostColumns.TABLE, null, values)
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)

        }
    }

    override fun likedById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun toShareById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               shares = shares + 1
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun toViewById(id: Long) {
        db.execSQL(
            """
                UPDATE  posts SET
                views = views + 1
                WHERE  id = ?;
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun deleteById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun findPostById(id: Long): Post {
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            val post = Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                author = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHOR)),
                content = getString(getColumnIndexOrThrow(PostColumns.COLUMN_CONTENT)),
                published = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHED)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKE_BY_ME)) != 0,
                likes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_NUMBER_OF_LIKES)),
                shares = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_NUMBER_OF_SHARE)),
                views = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_NUMBER_OF_VIEWS)),
                video = getString(getColumnIndexOrThrow(PostColumns.COLUMN_VIDEO))
            )
            return post
        }
    }
}