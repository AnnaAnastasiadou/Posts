package com.example.posts.data.mapper

import com.example.posts.data.local.database.Post
import com.example.posts.data.remote.PostDto

fun PostDto.toEntity(): Post {
    return Post(
        userId = userId,
        id = id,
        title = title,
        body = body
    )
}