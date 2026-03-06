package com.example.posts.feature

data class PostsUiState(
    val isLoading: Boolean = false,
    val data: List<PostUiModel>? = null,
    val error: String? = null
)