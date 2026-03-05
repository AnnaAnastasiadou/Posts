package com.example.posts.data.remote

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostDto(
    val userId: Int,
    val id: Int,
    @PrimaryKey
    val title: String,
    val body: String
)