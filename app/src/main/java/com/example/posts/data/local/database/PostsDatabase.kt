package com.example.posts.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.posts.data.remote.PostDto

@Database(
    entities = [PostDto::class],
    version = 1
)
abstract class PostsDatabase: RoomDatabase() {
}