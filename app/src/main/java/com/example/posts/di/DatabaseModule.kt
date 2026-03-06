package com.example.posts.di

import android.content.Context
import androidx.room.Room
import com.example.posts.data.local.database.PostDao
import com.example.posts.data.local.database.PostsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providePostsDatabase(@ApplicationContext context: Context): PostsDatabase =
        Room.databaseBuilder(
            context,
            PostsDatabase::class.java, "posts.db"
        ).build()

    @Provides
    fun providesPostDao(database: PostsDatabase): PostDao = database.postDao()
}