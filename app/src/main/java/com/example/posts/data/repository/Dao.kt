package com.example.posts.data.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.posts.data.remote.PostDto
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PostDto>)

    @Query("SELECT * FROM posts")
    fun getAll(): Flow<List<PostDto>>
}