package com.example.posts.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.posts.data.local.database.Post
import com.example.posts.data.local.database.PostDao

/*
*  Room database for the application
*
* Defines the database configuration and the list of entities stored in the database.
* Provides access to DAOs which contain the queries used to read and write data.
* Room generate the implementation of this database at compile time
*
*/
@Database(
    entities = [Post::class],
    version = 1
)
abstract class PostsDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao
}