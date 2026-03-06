package com.example.posts.data.remote

import retrofit2.http.GET

interface PostApi {
    @GET("posts")
    suspend fun fetchItems(): List<PostDto>
}