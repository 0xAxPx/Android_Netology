package com.peshale.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.peshale.nmedia.dto.Post
import com.peshale.nmedia.entity.PostEntity

@Dao
interface PostDao {

    @Query("""SELECT * FROM PostEntity ORDER BY id DESC""")
    fun getAll(): LiveData<List<PostEntity>>

    @Query(
        """
           UPDATE PostEntity SET
               likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = :id;
        """
    )
    fun likedById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               shares = shares + 1
           WHERE id = :id;
        """
    )
    fun toShareById(id: Long)

    @Query(
        """
           UPDATE PostEntity SET
               views = views + 1
           WHERE id = :id;
        """
    )
    fun toViewById(id: Long)

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content, video = :video WHERE id = :id")
    fun updateContentById(id: Long, content: String, video: String)

    fun addPost(post: PostEntity) =
        if (post.id == 0L) insert(post) else post.video?.let {
            updateContentById(post.id, post.content,
                it
            )
        }


    @Query("""DELETE FROM PostEntity WHERE id = :id""")
    fun deleteById(id: Long)

    @Query("""SELECT * FROM PostEntity WHERE id = :id""")
    fun findPostById(id: Long): Post
}