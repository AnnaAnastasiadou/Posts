package com.example.posts.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.posts.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        observePosts()
        checkInitialSync()
    }

    private fun checkInitialSync() {
        viewModelScope.launch {
            if (postsRepository.isEmpty()) {
                refreshPosts()
            }
        }
    }

    private fun observePosts() {
        viewModelScope.launch {
            postsRepository.observeItems().collect { posts ->
                _uiState.update {
                    it.copy(isLoading = false, error = null, data = posts.map { post ->
                        PostUiModel(
                            userId = post.userId,
                            id = post.id,
                            title = post.title,
                            body = post.body
                        )
                    })
                }
            }
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, data = null, error = null) }
            try {
                postsRepository.syncPosts()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        data = null,
                        error = "Error while fetching data"
                    )
                }
            }
        }
    }
}