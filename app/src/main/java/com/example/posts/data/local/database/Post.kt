package com.example.posts.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    val userId: Int,
    val id: Int,
    @PrimaryKey
    val title: String,
    val body: String
)