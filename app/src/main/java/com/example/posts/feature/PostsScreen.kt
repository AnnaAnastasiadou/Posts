package com.example.posts.feature

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.posts.data.local.database.Post

@Composable
fun PostsScreen() {
    PostsContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsContent() {
    Column {
        TopAppBar(
            title = {Text("Posts")},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Column( modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {}) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(painter = painterResource(R.drawable.stat_notify_sync), null)
                    Text("Sync")
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = dummyProducts) { post ->
                    PostCard(post)
                }
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card() {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(post.title.replaceFirstChar { it.uppercase() }, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(post.body, fontSize = 16.sp)
        }
    }
}

val dummyProducts = listOf(
    Post(
        userId = 1,
        id = 1,
        title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    ), Post(
        userId = 1,
        id = 1,
        title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    ), Post(
        userId = 1,
        id = 1,
        title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    ), Post(
        userId = 1,
        id = 1,
        title = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
        body = "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
    )
)

@Preview(showBackground = true)
@Composable
fun PostsScreenPreview() {
    PostsContent()
}