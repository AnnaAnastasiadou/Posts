package com.example.posts.data.repository

import com.example.posts.data.local.database.Post
import com.example.posts.data.local.database.PostDao
import com.example.posts.data.mapper.toEntity
import com.example.posts.data.remote.PostApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postApi: PostApi,
    private val postDao: PostDao
) : PostRepository {
    override suspend fun syncPosts() {
        val posts = postApi.fetchItems().map { dto -> dto.toEntity() }
        postDao.insertAll(posts)
    }

    override fun observeItems(): Flow<List<Post>> {
        return postDao.getAll()
    }

    override suspend fun isEmpty(): Boolean {
        return !postDao.hasPosts()
    }
}