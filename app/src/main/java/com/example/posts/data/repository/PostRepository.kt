package com.example.posts.data.repository

import com.example.posts.data.local.database.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun syncPosts()
    fun observeItems(): Flow<List<Post>>
    suspend fun isEmpty(): Boolean
}