package com.example.posts.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object) for accessing Post data in the Room database.
 * Contains methods for inserting and querying Post entities.
 */
@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertAll(items: List<Post>)

    @Query("SELECT * FROM posts")
    fun getAll(): Flow<List<Post>>
}